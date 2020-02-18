package com.backend.goodfinger.user;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class userController {
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/test")
    public String restTest() {
        System.out.println("dddd");
        return "hello";
    }
}
