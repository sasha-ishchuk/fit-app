package com.sasha.fitapp.validation.annotaion;

import com.sasha.fitapp.validation.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/*
custom annotation instead of Hibernate @Email
because Hibernate considers the old intranet addresses
format: address@myserver as valid, witch is not good for now
 */

@Target({TYPE, FIELD, METHOD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidEmail {
    String message() default "{validation.field.email}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
