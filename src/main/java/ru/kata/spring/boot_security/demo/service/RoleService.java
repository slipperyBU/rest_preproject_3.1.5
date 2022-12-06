package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
    Role findByName(String name);
    void save(Role role);
    Role findById(Long id);

}
