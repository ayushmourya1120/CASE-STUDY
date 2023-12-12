package com.jwt.example.controllers;

import com.jwt.example.modal.User;
import com.jwt.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserService userService;

    //    http://localhost:9098/home/users
    @GetMapping("/users")
    public List<User> user() {

        System.out.println("Getting Users");
        return this.userService.getUsers();

    }

    // Getting current logged-in user's name
    @GetMapping("/current-user")
    public String getLoggedInUser(Principal principal) {
        return principal.getName();
    }

}
