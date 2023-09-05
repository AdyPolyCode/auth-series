package com.poly.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {
    @GetMapping
    public String getDemo() {
        return "Demo message";
    }

    @GetMapping("role")
    public String getRole() {
        return "Demo Role message";
    }
}
