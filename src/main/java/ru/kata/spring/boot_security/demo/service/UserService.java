package ru.kata.spring.boot_security.demo.service;




import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    void saveUser(User user);

    void removeUserById(long id);

    List<User> getAllUsers();

    User getUserById(long id);

    User getFirstUserByEmail(String name);

    void saveRole(Role role);

    Role getRoleByName(String role);

}
