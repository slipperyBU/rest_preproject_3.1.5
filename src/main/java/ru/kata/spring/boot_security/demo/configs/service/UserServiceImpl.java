package ru.kata.spring.boot_security.demo.configs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.configs.models.Role;
import ru.kata.spring.boot_security.demo.configs.models.User;
import ru.kata.spring.boot_security.demo.configs.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.configs.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.configs.security.UserDetailsImpl;


import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository , @Lazy PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    @Override
    public List<User> index() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public boolean save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public User show(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(int id,User updatedUser) {
        User user = userRepository.getById(id);

        user.setName(updatedUser.getName());
        user.setLastName(updatedUser.getLastName());
        user.setAge(updatedUser.getAge());
        user.setEmail(updatedUser.getEmail());
        user.setAddress(updatedUser.getAddress());
        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        userRepository.flush();
    }

    @Override
    public User show(String name) {
        return userRepository.findByName(name);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     User user = userRepository.findByName(username);

     if (user == null) {
         throw new UsernameNotFoundException("User not located");
     }
     return new UserDetailsImpl(user);
 }
}
