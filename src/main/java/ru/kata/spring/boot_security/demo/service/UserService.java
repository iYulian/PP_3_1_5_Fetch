package ru.kata.spring.boot_security.demo.service;




import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {


    void saveUser(User user, String authority1, String authority2);

    void removeUserById(long id);

    List<User> getAllUsers();

    public User getUserById(long id);

    User getFirstUserByName(String name);

}
