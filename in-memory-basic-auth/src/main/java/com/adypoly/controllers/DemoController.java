package com.adypoly.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {
    private static List<String> messages = new ArrayList<>(){{
        add("Hello there!");
        add("What's up!");
        add("Hi!");
    }};

    @GetMapping
    public List<String> getAll() {
        return messages;
    }

    @GetMapping("free")
    public String getNoAuthMessage() {
        return "Demo Message";
    }

    @GetMapping("role")
    public String getRoleAuthMessage() {
        return "Demo Role Message";
    }

    @GetMapping("{id}")
    public String getAuthMessage(@PathVariable Short id) {
        if(id >= messages.size()) {
            return "Wrong msg id";
        }

        String message = messages.get(id);

        return message;
    }
}
