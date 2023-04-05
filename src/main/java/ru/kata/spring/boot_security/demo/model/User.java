package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String lastname;

    @Column
    private byte age;

    // Если ставлю ленивую инициализацию, то получаю исключение
    // org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role:
    // ru.kata.spring.boot_security.demo.model.User.authority, could not initialize proxy - no Session
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Role> authority;

    public User() {
        authority = new HashSet<>();
    }

    public User(String name, String lastname, byte age) {
        authority = new HashSet<>();
        this.name = name;
        this.lastname = lastname;
        this.age = age;
    }

    public void userAddAuthority(String stringRole) {
        Role role = new Role(stringRole);
        authority.add(role);
        role.setUser(this);
    }

    public void userClearAuthority() {
        authority = new HashSet<>();
    }
    public Set<Role> getRoles() {
        return authority;
    }

    public void setRoles(Set<Role> roles) {
        this.authority = roles;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authority;
    }

    @Override
    public String getPassword() {
        return lastname;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}