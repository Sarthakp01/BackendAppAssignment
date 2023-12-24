package com.example.SpringApp.controller;

import com.example.SpringApp.models.AllocateFunds;
import com.example.SpringApp.models.AuthenticateRequest;
import com.example.SpringApp.models.User;
import com.example.SpringApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {

        return userService.registerUser(user);
    }

    @PostMapping("/authenticate")
    public String authenticate(
            @RequestBody AuthenticateRequest request) {
        return userService.authenticateUser(request);
    }

    @PostMapping("/addFunds")
    public String addFunds(@RequestBody AllocateFunds funds) {
        return userService.allocateFunds(funds);
    }

    @PostMapping("/addOfflineFunds")
    public String addOfflineFunds(@RequestBody AllocateFunds funds) {
        return userService.allocateOfflineFunds(funds);
    }
}
