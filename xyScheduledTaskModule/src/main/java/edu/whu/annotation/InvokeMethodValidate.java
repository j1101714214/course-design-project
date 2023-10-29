package edu.whu.annotation;


import edu.whu.model.common.enumerate.InvokeMethod;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Akihabara
 * @version 1.0
 * @description InvokeMethodNotEmpty: 校验InvokeMethod的注解
 * @date 2023/10/5 19:29
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {InvokeMethodValidate.InvokeMethodValidator.class})
public @interface InvokeMethodValidate {
    /**
     * 错误信息提示
     */
    String message() default "InvokeMethod params is empty or invalid";

    /**
     * 校验分组
     */
    Class<?>[] groups() default {};


    Class<? extends Payload>[] payload() default {};

    class InvokeMethodValidator implements ConstraintValidator<InvokeMethodValidate, Enum<InvokeMethod>> {
        @Override
        public void initialize(InvokeMethodValidate constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
        }

        @Override
        public boolean isValid(Enum<InvokeMethod> value, ConstraintValidatorContext context) {
            return value instanceof InvokeMethod;
        }
    }
}
