package com.ztp.notes.controller;

import com.ztp.notes.model.User;
import com.ztp.notes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User addUser(@RequestBody User user) {
        userService.saveUser(user);
        return user;
    }

    @PostMapping("/users")
    public User loginUser(@RequestBody User user) {
        return userService.loginUser(user);
    }
}
