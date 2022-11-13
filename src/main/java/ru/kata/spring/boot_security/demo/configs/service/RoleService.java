package ru.kata.spring.boot_security.demo.configs.service;

import ru.kata.spring.boot_security.demo.configs.models.Role;

import java.util.List;

public interface RoleService {
    List<Role> index();
    Role show(String name);

}
