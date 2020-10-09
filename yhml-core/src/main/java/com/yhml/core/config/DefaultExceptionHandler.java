package com.yhml.core.config;

import com.yhml.core.base.ErrorMessge;
import com.yhml.core.base.bean.Result;
import com.yhml.core.exception.AuthException;
import com.yhml.core.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import static com.yhml.core.base.ErrorMessge.ERROR_SYS;


/**
 * 默认异常处理类
 */
@Slf4j
@RestControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    // @Resource
    // protected MessageSource messageSource;

    @Autowired
    protected HttpServletRequest request;

    // @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public Result handleBindException(BindException e, MethodArgumentNotValidException ex) {
        log.error("handleBindException", e);
        Result result = Result.createBuilder(ErrorMessge.ERROR_ARGS).build();

        // 设参数绑定错误的具体信息
        if (e.hasFieldErrors()) {
            FieldError field = e.getFieldErrors().get(0);
            result.setMsg(getError(field));
        }

        if (ex != null && ex.getBindingResult().hasFieldErrors()) {
            FieldError field = ex.getBindingResult().getFieldError();
            result.setMsg(getError(field));
        }

        // ModelAndView mv = new ModelAndView();
        // mv.setView(new MappingJackson2JsonView());
        // mv.addObject("resultCode", PARAMS_ERROR.getCode());
        // mv.addObject("resultDesc", PARAMS_ERROR.getMsg());
        // return mv;

        return result;
    }

    private String getError(FieldError field) {
        String msg = field.getRejectedValue() != null ? field.getDefaultMessage() : field.getField() + ":" + field.getDefaultMessage();
        return msg;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({AuthException.class})
    public Result handleAuthException(AuthException e) {
        log.error("handleAuthException URI:{}", request.getRequestURI(), e);
        return Result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public Result handleException(Exception e) {
        log.error("handleException URI:{}", request.getRequestURI(), e);

        Result.ResultBuilder<Object> builder = Result.builder();

        if (e instanceof BaseException) {
            BaseException ex = (BaseException) e;
            // 读取配置在 validator.properties 的 Message
            // String msg = messageSource.getMessage(e.getMessage(), null, e.getMessage(), Locale.CHINA);
            builder.code(ex.getCode()).msg(ex.getMessage());
        } else {
            builder = Result.createBuilder(ERROR_SYS);
            customHandleException(e, builder);
        }

        return builder.build();
    }

    protected void customHandleException(Exception ex, Result.ResultBuilder builder) {
    }

    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleException {}", request);

        Result.ResultBuilder builder = Result.builder().code(String.valueOf(status.value())).msg(ex.getMessage());

        // if (ex instanceof BindException) {
        //     BindException e = (BindException) ex;
        //     if (e.hasFieldErrors()) {
        //         FieldError field = e.getFieldErrors().get(0);
        //         builder.msg(getError(field)).code(ErrorMessge.ERROR_ARGS.getCode());
        //     }
        // }
        //
        // if (ex instanceof MethodArgumentNotValidException) {
        //     MethodArgumentNotValidException e = (MethodArgumentNotValidException) ex;
        //     if (e != null && e.getBindingResult().hasFieldErrors()) {
        //         FieldError field = e.getBindingResult().getFieldError();
        //         builder.msg(getError(field)).code(ErrorMessge.ERROR_ARGS.getCode());
        //     }
        // }

        if (ex instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException e = (MethodArgumentTypeMismatchException) ex;
            String msg = e.getName() + "=" + e.getValue();
            log.error("Method:{}, Arg:{}", e.getParameter().getMethod().getName(), msg);
            builder = Result.createBuilder(ErrorMessge.ERROR_ARGS).extMsg(msg);
        }

        // log.error("message:{}", ex.getMessage());

        return new ResponseEntity<>(builder.build(), HttpStatus.OK);
    }
}
