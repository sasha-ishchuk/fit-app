package com.sasha.fitapp;

import com.sasha.fitapp.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class PasswordValidationTest {

    @Test
    public void testInvalidPassword() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("firstName_test1");
        userDto.setLastName("lastName_test1");
        userDto.setEmail("user@test1.com");
        userDto.setPassword("password_test1");
        userDto.setPasswordConfirm("password_test1");

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);

        Assertions.assertEquals(constraintViolations.size(), 2);


    }

    @Test
    public void testValidPasswords() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("firstName_test2");
        userDto.setLastName("lastName_test2");
        userDto.setEmail("user@test2.com");
        userDto.setPassword("Password%test2");
        userDto.setPasswordConfirm("Password%test2");

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);

        Assertions.assertEquals(constraintViolations.size(), 0);
    }
}

