package com.rlve.matcher.api.controllers;

import com.rlve.matcher.api.services.MatchService;
import com.rlve.matcher.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService ) {
        this.userService = userService;
    }

}
