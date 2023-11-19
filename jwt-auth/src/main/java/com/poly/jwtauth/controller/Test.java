package com.poly.jwtauth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class Test {

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String user() {
        return "Welcome user";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String admin() {
        return "Welcome admin";
    }

    @GetMapping("/test")
    public String test() {
        return "Welcome test";
    }
}
