package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String index(Principal principal, Model model) {
        User user = userService.show(principal.getName());
        model.addAttribute("users", userService.index());
        model.addAttribute("userData",user);
        model.addAttribute("listOfRoles", roleService.index());
        return "index";
    }

    @GetMapping("/addNewUser")
    public String addUser(Model model, Principal principal) {
        model.addAttribute("user", new User());
        model.addAttribute("userInfo",userService.show(principal.getName()));
        model.addAttribute("listOfRoles", roleService.index());
        return "new";
    }


    @PostMapping("/saveUser")
    public String create(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }


    @PatchMapping("/update/{id}")
    public String update(User user){
        this.userService.update(user.getId(),user);
        return "redirect:/admin";

    }

    @DeleteMapping("/deleteUser/{id}")
    public String delete(User user){
        userService.delete(user.getId());
        return "redirect:/admin";
    }
}
