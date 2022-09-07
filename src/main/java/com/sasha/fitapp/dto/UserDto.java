package com.sasha.fitapp.dto;

import com.sasha.fitapp.validation.annotaion.PasswordMatches;
import com.sasha.fitapp.validation.annotaion.ValidEmail;
import com.sasha.fitapp.validation.annotaion.ValidPassword;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@PasswordMatches(first = "password", second = "passwordConfirm")
public class UserDto implements Serializable {

    @NotNull
    @NotEmpty(message = "{user.firstName.notEmpty}")
    @Size(min = 1, max = 20, message = "{user.firstName.size}")
    private String firstName;

    @NotNull
    @NotEmpty(message = "{user.lastName.notEmpty}")
    @Size(min = 1, max = 20, message = "{user.lastName.size}")
    private String lastName;

    @NotNull
    @NotEmpty(message = "{user.email.notEmpty}")
    @ValidEmail
    private String email;

    @NotNull
    @NotEmpty(message = "{user.password.notEmpty}")
    @ValidPassword
    private String password;

    @NotNull
    @NotEmpty(message = "{user.passwordConfirm.notEmpty}")
    @ValidPassword
    private String passwordConfirm;

    public UserDto(String firstName, String lastName, String email, String password) {
    }
}
