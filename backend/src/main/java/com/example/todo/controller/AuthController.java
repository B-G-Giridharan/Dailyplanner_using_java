package com.example.todo.controller;

import com.example.todo.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private Map<String, String> users = new HashMap<>();

    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        if (users.containsKey(user.getUsername())) {
            return "USER_EXISTS";
        }
        users.put(user.getUsername(), user.getPassword());
        return "SUCCESS";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        if (users.containsKey(user.getUsername()) &&
            users.get(user.getUsername()).equals(user.getPassword())) {
            return "SUCCESS";
        }
        return "FAIL";
    }
}
