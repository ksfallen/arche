package com.yhml.core.validation;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 枚举类型校验
 * @author Jfeng
 * @date 2020/5/7
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EnumValidtor.class})
@Documented
public @interface EnumValid {
    String message() default "类型错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> type();
}
