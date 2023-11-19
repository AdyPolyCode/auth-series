package com.poly.jwtauth.controller;

import com.poly.jwtauth.entity.CustomUser;
import com.poly.jwtauth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public String login(@RequestBody CustomUser user) {
        return authService.login(user);
    }

    @PostMapping("/register")
    public String register(@RequestBody CustomUser user) {
        return authService.register(user);
    }
}
