package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user")
    public String addUser(ModelMap model, Principal principal) {
        User user = userService.getFirstUserByEmail(principal.getName());
        model.addAttribute("user", user);
        return "userPage";
    }

    @RequestMapping(value = "/admin")
    public String scripts(ModelMap model) {
        return "adminPage";
    }
}
