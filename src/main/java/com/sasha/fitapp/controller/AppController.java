package com.sasha.fitapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController  {

    @GetMapping("/")
    public String getMainPage(Model model){
        model.addAttribute("name", "FitApp");
        return "main";
    }

    @GetMapping("/login")
    public String getLoginForm(){
        return "login";
    }

    @GetMapping("/redirection")
    public String getRedirectPage(){
        return "redir";
    }

}
