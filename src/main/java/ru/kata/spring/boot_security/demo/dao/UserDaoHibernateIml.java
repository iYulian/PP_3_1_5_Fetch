package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class UserDaoHibernateIml implements UserDaoHibernate {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void saveUser(User user, String authority1, String authority2) {
        if (!authority1.isEmpty()) {
            user.userAddAuthority(Role.valueOf(authority1));
        }
        if (!authority2.isEmpty()) {
            user.userAddAuthority(Role.valueOf(authority2));
        }
        if (user.getId() == 0) {
            entityManager.persist(user);
        } else {
            entityManager.merge(user);
        }
    }

    @Override
    public void removeUserById(long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);

    }

    @Override
    public List<User> getAllUsers() {

        List<User> userList = entityManager.createQuery("from User", User.class).getResultList();
        return userList;

    }

    @Override
    public User getUserById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User getFirstUserByName(String name) {
        List<User> userList = entityManager.createQuery("from User where name = '" + name + "'", User.class).getResultList();
        return  userList.get(0);
    }
}
