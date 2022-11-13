package ru.kata.spring.boot_security.demo.configs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.configs.models.Role;
import ru.kata.spring.boot_security.demo.configs.repositories.RoleRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> index() {
        return roleRepository.findAll();
    }

    @Override
    public Role show(String name) {
        return roleRepository.findByName(name);
    }
}
