package ru.kata.spring.boot_security.demo.configs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.configs.models.Role;
import ru.kata.spring.boot_security.demo.configs.models.User;
import ru.kata.spring.boot_security.demo.configs.service.RoleService;
import ru.kata.spring.boot_security.demo.configs.service.UserService;

import java.security.Principal;
import java.util.List;
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
        Role roleInfo = roleService.index().get(0);
        Role foundRole = roleService.show(roleInfo.getName());
        model.addAttribute("roleInfo",roleInfo);
        return "index";
    }

    @GetMapping("/addNewUser")
    public String addUser(Model model, Principal principal) {
        User user = new User();
        User userInfo = userService.show(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("userInfo",userInfo);
        model.addAttribute("listOfRoles", roleService.index());
        return "new";
    }


    @PostMapping("/saveUser")
    public String create(@ModelAttribute("user") User user) {
        user.setRoles(user.getRoles().stream().map(r->roleService.show(r.getName())).toList());
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("user", userService.show(id));
        return "editor";
    }
    @PatchMapping("/update/{id}")
    public String update(@ModelAttribute("user") User user,@PathVariable("id") int id){
        this.userService.update(id,user);
        return "redirect:/admin";

    }

    @GetMapping("/deleteUser/{id}")
    public String delete(@ModelAttribute("user") User user, @PathVariable("id") int id){
        userService.delete(id);
        return "redirect:/admin";
    }
}
