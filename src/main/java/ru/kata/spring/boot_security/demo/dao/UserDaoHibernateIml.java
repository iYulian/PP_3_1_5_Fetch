package ru.kata.spring.boot_security.demo.dao;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class UserDaoHibernateIml implements UserDaoHibernate {

    @PersistenceContext
    private EntityManager entityManager;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public void saveUser(User user) {


        if (user.getId() != 0) {
            if (user.getRoles().equals(getUserById(user.getId()).getRoles()) ||
                    user.getRoles().size() < getUserById(user.getId()).getRoles().size()) {
                user.setRoles(getUserById(user.getId()).getRoles());
            }
            user.userAddAuthority(getUserById(user.getId()).getRoles());
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getUserPassword()));

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
    public User getFirstUserByEmail(String userEmail) {
        List<User> userList = entityManager.
                createQuery("from User where email = '" + userEmail + "'", User.class).getResultList();
        if (userList.isEmpty()){
            return null;
        }
        return  userList.get(0);
    }
}
