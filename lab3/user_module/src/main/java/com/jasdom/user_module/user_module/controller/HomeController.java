package com.jasdom.user_module.user_module.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController{

    @GetMapping("/")
    public String showRootPage(ModelMap model) {
        return "home";
    }

}
