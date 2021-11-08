package com.jasdom.user_module.user_module.controller;

import com.jasdom.user_module.user_module.model.User;
import com.jasdom.user_module.user_module.service.ServiceResponse;
import com.jasdom.user_module.user_module.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    UserService service;

    @GetMapping("/list-users")
    public String showAll(ModelMap model) {
        model.put("users", service.findAll());
        return "list-users";
    }

    @GetMapping("/add-user")
    public String showAddPage(ModelMap model) {
        model.addAttribute("user", new User("", "", "", "", "", ""));
        return "user";
    }

    @PostMapping("/add-user")
    public String add(ModelMap model, @ModelAttribute("user") User user, BindingResult result) {
        if(result.hasErrors()) {
            return "user";
        }
        ServiceResponse response = service.add(user);

        if(!response.success){
            model.addAttribute("error", transformMessageToHTML(response.message));
            return "user";
        }else return "redirect:/list-users";
    }

    @GetMapping("/update-user/{userId}")
    public String showUpdatePage(ModelMap model, @PathVariable long userId) {
        Optional<User> found = service.findById(userId);
        if(found.isPresent()){
            model.addAttribute("user", found.get());
            return "user";
        }
        return "redirect:/list-users";
    }

    @PostMapping("/update-user/{userId}")
    public String update(ModelMap model, @ModelAttribute("user") User user, BindingResult result) {
        if(result.hasErrors()) {
            return "user";
        }
        ServiceResponse<User> response = service.update(user);

        if(!response.success){
            model.addAttribute("error", transformMessageToHTML(response.message));
            return "user";
        }else return "redirect:/list-users";
    }

    @GetMapping("/delete-user/{userId}")
    public String delete(@PathVariable long userId) {
        service.deleteById(userId);
        return "redirect:/list-users";
    }

    private String transformMessageToHTML(String message){
        return message.replace(". ", "<br />");
    }
}
