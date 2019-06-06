import com.yhml.core.util.JsonUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

#set($symbol_pound='#')
        #set($symbol_dollar='$')
        #set($symbol_escape='\' ) package ${package}.biz.exception;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 4595060073570956480L;

    private String code;
    private String message;

    public BaseException(ErrorMessge messge) {
        this.code = messge.getCode();
        this.message = messge.getMessage();
    }

    public BaseException(String msg) {
        this.code = ErrorMessge.ERROR_SYS.getCode();
        this.message = msg;
    }

    @Override
    public String toString() {
        return JsonUtil.toJsonString(this);
    }
}
