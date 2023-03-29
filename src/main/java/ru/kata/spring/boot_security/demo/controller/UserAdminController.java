package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class UserAdminController {

	private final UserService userService;

	public UserAdminController(UserService userService) {
		this.userService = userService;
	}


	@GetMapping(value = "/users")
	public String getUsers(ModelMap model) {
		model.addAttribute("users", userService.getAllUsers());
		return "users";

	}

	@RequestMapping(value = "/addUser")
	public String addUser(ModelMap model) {
		model.addAttribute("user", new User());
		return "saveUser";
	}

	@RequestMapping(value = "/saveUser")
	public String saveUser(@ModelAttribute("user") User user, @ModelAttribute("isUser") String isUser,
						   @ModelAttribute("isAdmin") String isAdmin) {
		userService.saveUser(user, isUser, isAdmin);
		return "redirect:/admin/users";
	}

	@RequestMapping(value = "/updateUser", produces = "text/html; charset=utf-8")
	public String updateUser(@ModelAttribute("id") long id, ModelMap model) {
		model.addAttribute("user", userService.getUserById(id));
		return "saveUser";
	}

	@RequestMapping(value = "/deleteUser")
	public String deleteUser(@ModelAttribute("id") long id) {
		userService.removeUserById(id);
		return "redirect:/admin/users";
	}

	
}