package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class UserAdminController {

	private final UserService userService;

	public UserAdminController(UserService userService) {
		this.userService = userService;
	}


	@GetMapping(value = "/admin")
	public String getUsers(ModelMap model, Principal principal, @ModelAttribute(name = "user") User user) {
		User admin = userService.getFirstUserByEmail(principal.getName());
		model.addAttribute("admin", admin);
		model.addAttribute("users", userService.getAllUsers());
		model.addAttribute("user", user);
		model.addAttribute("user1", new User());
		return "adminPage";

	}

	@RequestMapping(value = "/saveUser")
	public String saveUser(@ModelAttribute("user") User user) {
		System.out.println(user);
		userService.saveUser(user);
		return "redirect:/admin";
	}

	@RequestMapping(value = "/updateUser", produces = "text/html; charset=utf-8")
	public String updateUser(@ModelAttribute("id") long id, ModelMap model) {
		model.addAttribute("user", userService.getUserById(id));
		return "redirect:saveUser";
	}

	@RequestMapping(value = "/deleteUser")
	public String deleteUser(@ModelAttribute("id") long id) {
		userService.removeUserById(id);
		return "redirect:/admin";
	}

	
}