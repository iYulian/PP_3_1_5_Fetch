package ru.kata.spring.boot_security.demo.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDaoHibernate;
import ru.kata.spring.boot_security.demo.model.Role;
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
    public void saveUser(User user) {
        userDao.saveUser(user);
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
    @Transactional
    public User getUserById(long id) {
        return userDao.getUserById(id);
    }

    @Override
    @Transactional
    public User getFirstUserByEmail(String name) {
        return userDao.getFirstUserByEmail(name);
    }

    @Override
    @Transactional
    public void saveRole(Role role) {
        userDao.saveRole(role);
    }

    @Override
    @Transactional
    public Role getRoleByName(String role) {
        return userDao.getRoleByName(role);
    }
}
