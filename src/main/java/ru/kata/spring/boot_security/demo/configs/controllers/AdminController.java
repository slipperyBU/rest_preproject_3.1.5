package ru.kata.spring.boot_security.demo.configs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.configs.models.User;
import ru.kata.spring.boot_security.demo.configs.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", userService.index());
        return "index";
    }

    @GetMapping("/addNewUser")
    public String addUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "new";
    }

    @PostMapping("/saveUser")
    public String create(@ModelAttribute("user") User user) {
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
