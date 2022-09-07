package com.sasha.fitapp.validation.annotaion;

import com.sasha.fitapp.validation.MyPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, METHOD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = MyPasswordValidator.class)
@Documented
public @interface ValidPassword {
    String message() default "{validation.field.password}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
