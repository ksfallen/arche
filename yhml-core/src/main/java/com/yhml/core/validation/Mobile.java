package com.yhml.core.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import com.yhml.core.util.ValidateUtil;

import static java.lang.annotation.ElementType.*;

/**
 * @author: Jfeng
 * @date: 2018/4/25
 */

@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {Mobile.MobileValidator.class})
public @interface Mobile {
    String message() default "手机号码格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class MobileValidator implements ConstraintValidator<Mobile, String> {

        @Override
        public void initialize(Mobile constraintAnnotation) {

        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return ValidateUtil.isMobile(value);
        }

    }

}
