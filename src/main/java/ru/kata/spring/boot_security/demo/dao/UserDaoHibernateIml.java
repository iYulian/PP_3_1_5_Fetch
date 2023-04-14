package ru.kata.spring.boot_security.demo.dao;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Repository
public class UserDaoHibernateIml implements UserDaoHibernate {

    @PersistenceContext
    private EntityManager entityManager;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public void saveUser(User user) {

        Role adminRole = getRoleByName("ROLE_ADMIN");
        Role userRole = getRoleByName("ROLE_USER");
        Set<Role> roleSet = new HashSet<>();

        if (user.getRoles().contains(adminRole) & user.getRoles().contains(userRole)) {
            roleSet.add(adminRole);
            roleSet.add(userRole);
            user.setRoles(roleSet);
        } else {
            if (user.getRoles().contains(adminRole)) {
                roleSet.add(adminRole);
                user.setRoles(roleSet);
            }
            if (user.getRoles().contains(userRole)) {
                roleSet.add(userRole);
                user.setRoles(roleSet);
            }
        }
        if (user.getPassword().isEmpty()) {
            user.setPassword(getUserById(user.getId()).getPassword());
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getUserPassword()));
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
    public User getFirstUserByEmail(String userEmail) {
        List<User> userList = entityManager.
                createQuery("from User where email = '" + userEmail + "'", User.class).getResultList();
        if (userList.isEmpty()){
            return null;
        }
        return  userList.get(0);
    }

    @Override
    public void saveRole(Role role) {
        if (role.getId() == 0) {
            entityManager.persist(role);
        } else {
            entityManager.merge(role);
        }
    }

    @Override
    public Role getRoleByName(String role) {
        List<Role> userList = entityManager.
                createQuery("from Role where role = '" + role + "'", Role.class).getResultList();
        if (userList.isEmpty()){
            return null;
        }
        return  userList.get(0);
    }
}
