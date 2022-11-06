package ru.kata.spring.boot_security.demo.configs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.configs.dao.UserDAO;
import ru.kata.spring.boot_security.demo.configs.models.Role;
import ru.kata.spring.boot_security.demo.configs.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.security.UserDetailsImpl;


import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, @Lazy PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    @Override
    public List<User> index() {
        return userDAO.index();
    }

    @Override
    public boolean save(User user) {
        userDAO.save(user);
        return true;
    }

    @Override
    public User show(int id) {
        return userDAO.show(id);
    }

    @Override
    public void delete(int id) {
        userDAO.delete(id);
    }

    @Override
    public void update(int id,User updatedUser) {
        userDAO.update(id,updatedUser);
    }

    @Override
    public User show(String name) {
        return userDAO.show(name);
    }

 @Override
 @Transactional
 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     User user = userDAO.show(username);

     if (user == null) {
         throw new UsernameNotFoundException("User not located");
     }
     return new UserDetailsImpl(user);
 }

}
