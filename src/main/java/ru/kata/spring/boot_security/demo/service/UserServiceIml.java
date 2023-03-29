package ru.kata.spring.boot_security.demo.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDaoHibernate;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
public class UserServiceIml implements UserService{

    private final UserDaoHibernate userDao;

    public UserServiceIml(UserDaoHibernate userDao) {
        this.userDao = userDao;
    }


    @Override
    @Transactional
    public void saveUser(User user, String authority1, String authority2) {
        userDao.saveUser(user, authority1, authority2);
    }

    @Override
    @Transactional
    public void removeUserById(long id) {
        userDao.removeUserById(id);
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getUserById(long id) {
        return userDao.getUserById(id);
    }

    @Override
    public User getFirstUserByName(String name) {
        return userDao.getFirstUserByName(name);
    }
}
