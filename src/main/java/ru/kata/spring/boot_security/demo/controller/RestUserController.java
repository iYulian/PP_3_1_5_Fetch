package ru.kata.spring.boot_security.demo.controller;

import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.restModel.RestUserModel;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.LinkedList;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/rest/")
public class RestUserController {

    private final UserService userService;
    public RestUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<RestUserModel> getUsers() {
        List<RestUserModel> userModels = new LinkedList<>();
        userService.getAllUsers().forEach((x) -> {userModels.add(new RestUserModel(x));
        });
        System.out.println(userModels);
        return userModels;
    }

    @GetMapping("/users/{id}")
    public RestUserModel getUser(@PathVariable long id) {
        return new RestUserModel(userService.getUserById(id));
    }

    @PostMapping("/users")
    public RestUserModel addUser(@RequestBody RestUserModel userModel) {
        userService.saveUser(new User(userModel));
        return userModel;
    }

    @PutMapping("/users")
    public RestUserModel updateUser(@RequestBody RestUserModel userModel) {
        userService.saveUser(new User(userModel));
        return userModel;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.removeUserById(id);
    }
}
