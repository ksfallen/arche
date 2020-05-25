package com.yhml.core.validation;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Jfeng
 * @date 2020/5/7
 */
@Slf4j
public class EnumValidtor implements ConstraintValidator<EnumValid, String> {
    /**
     * 枚举类
     */
    private Class<?> clazz;

    @Override
    public void initialize(EnumValid constraintAnnotation) {
        clazz = constraintAnnotation.type();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!clazz.isEnum()) {
            throw new RuntimeException("EnumValid type 不是枚举类:" + clazz.getName());
        }
        try {
            Object[] constants = clazz.getEnumConstants();
            Method valuesMethod = clazz.getMethod("getValue");

            for (Object constant : constants) {
                Object invoke = valuesMethod.invoke(constant);
                if (StringUtils.equals(invoke.toString(), value)) {
                    return true;
                }
            }
        } catch (Exception e) {
            log.error("枚举类型校验出错", e);
        }

        return false;
    }
}
