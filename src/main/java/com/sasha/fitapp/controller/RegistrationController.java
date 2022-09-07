package com.sasha.fitapp.controller;

import com.sasha.fitapp.dto.UserDto;
import com.sasha.fitapp.model.User;
import com.sasha.fitapp.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Valid;

@Controller
public class RegistrationController implements WebMvcConfigurer {

    private final UserService userService;

    public RegistrationController(@Lazy UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String getRegistrationForm(@ModelAttribute("userDto") UserDto userDto){
        return "registration";
    }

    @PostMapping("/registration")
    public String getValidUserRegister(@Valid UserDto userDto, BindingResult bindingResult){

        User existingUser = userService.findUserByEmail(userDto.getEmail());
        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            bindingResult.reject("email", null,
                    "User with this e-mail is already registered!");
        }

        if(bindingResult.hasErrors()){
            return "registration";
        }

        userService.saveUser(userDto);
        return "redirect:/registration?success";
    }
}
