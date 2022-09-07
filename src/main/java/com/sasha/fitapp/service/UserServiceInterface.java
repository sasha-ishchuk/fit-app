package com.sasha.fitapp.service;

import com.sasha.fitapp.dto.UserDto;
import com.sasha.fitapp.model.User;

import java.util.List;

public interface UserServiceInterface {

    User saveUser(UserDto userDto);

    User findUserByEmail(String email);

    User findUserById(long id);

    List<UserDto> findAllUsers();

    String getEmail();
}
