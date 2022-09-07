package com.sasha.fitapp.validation;

import com.sasha.fitapp.validation.annotaion.ValidPassword;
import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
password must contain all the following (not in particular order):
1. At least one digit [0-9]
2. At least one lowercase character [a-z]
3. At least one uppercase character [A-Z]
4. At least one special character [*.!@#$%^&(){}[]:;<>,.?/~_+-=|\]
5. At least 8 characters in length, but no more than 32.
6. No whitespaces
 */

public class MyPasswordValidator implements ConstraintValidator<ValidPassword, String> {


    @Override
    public void initialize(ValidPassword constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 30),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new WhitespaceRule()
        ));

        RuleResult result = validator.validate(new PasswordData(password));

        return result.isValid();

//        List<String> messages = validator.getMessages(result);
//        String messageTemplate = String.join(",", messages);
//        constraintValidatorContext.buildConstraintViolationWithTemplate(messageTemplate)
//                .addConstraintViolation()
//                .disableDefaultConstraintViolation();
    }
}
