package ru.kata.spring.boot_security.demo.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

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

    @Column
    private String email;

    @Column
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> authority;


    public User() {
        authority = new HashSet<>();
    }

    public User(String name, String lastname, byte age, String email, String password) {
        authority = new HashSet<>();
        this.name = name;
        this.lastname = lastname;
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public void userAddAuthority(String stringRole) {
        Role role = new Role(stringRole);
        authority.add(role);
    }
    public void userAddAuthority(Set<Role> roleList) {
        authority.addAll(roleList);
    }

    public String getRolesToString() {
        StringBuffer stringBuffer = new StringBuffer();
        authority.forEach(stringBuffer::append);
        return stringBuffer.toString();
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

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", authority=" + authority +
                '}';
    }

    @Override
    @Transactional
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authority;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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