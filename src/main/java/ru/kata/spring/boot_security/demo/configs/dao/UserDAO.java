package ru.kata.spring.boot_security.demo.configs.dao;

import ru.kata.spring.boot_security.demo.configs.models.User;

import java.util.List;

public interface UserDAO {
    List<User> index();

    boolean save(User user);

    User show(int id);

    void update(int id,User updatedUser);

    void delete(int id);

    User show(String name);
}
