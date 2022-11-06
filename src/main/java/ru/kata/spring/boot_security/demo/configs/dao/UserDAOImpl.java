package ru.kata.spring.boot_security.demo.configs.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.configs.models.Role;
import ru.kata.spring.boot_security.demo.configs.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext(unitName = "entityManagerFactory")
    private  EntityManager entityManager;

    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Autowired
    public UserDAOImpl(@Lazy PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    @Transactional
    @Override
    public List<User> index() {
        List<User> users = entityManager.createQuery("FROM User", User.class).getResultList();
        return users;
    }

    @Transactional
    @Override
    public boolean save(User user) {
        user.setRoles(Collections.singleton(new Role(1,"ROLE_USER")));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        entityManager.persist(user);
        return true;
    }
    @Transactional
    @Override
    public User show(int id) {
        return entityManager.find(User.class,id);
    }
    @Transactional
    @Override
    public void update(int id,User updatedUser) {
        User user = show(id);

        user.setName(updatedUser.getName());
        user.setLastName(updatedUser.getLastName());
        user.setAge(updatedUser.getAge());
        user.setEmail(updatedUser.getEmail());
        user.setAddress(updatedUser.getAddress());
        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
    }
    @Transactional
    @Override
    public void delete(int id) {
        entityManager.remove(entityManager.find(User.class,id));
    }

    @Override
    public User show(String name) {
        return entityManager.createQuery("FROM User where name=:userName", User.class).setParameter("userName",name).getSingleResult();
    }
}
