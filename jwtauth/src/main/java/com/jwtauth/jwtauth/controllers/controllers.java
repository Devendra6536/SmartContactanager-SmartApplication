package com.jwtauth.jwtauth.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwtauth.jwtauth.userService;
import com.jwtauth.jwtauth.Model.User;

@RestController
@RequestMapping("/home")
public class controllers {

    @Autowired
    private userService userService;

    @GetMapping("/user")
    public List<User> home() {
        System.err.println("getting users");
        return this.userService.getAllUsers();
    }

    @RequestMapping("/current_user")
    public String getLogedUser(Principal principal) {
        System.out.println("CURRENT loged in user " + principal.getName());
        return principal.getName();
    }
}
