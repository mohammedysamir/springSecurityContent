package com.security.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/main")
public class MainController {
    @GetMapping("/")
    public String hello() {
        return "Hello Peter!";
    }
}
