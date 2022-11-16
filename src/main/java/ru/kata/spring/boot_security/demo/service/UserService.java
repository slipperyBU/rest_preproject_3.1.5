package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> index();
    boolean save(User user);
    User show(int id);

    void update(int id,User updatedUser);

    void delete(int id);

  @Override
  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

  User show(String email);
}
