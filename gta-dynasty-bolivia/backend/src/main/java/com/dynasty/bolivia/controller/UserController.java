package com.dynasty.bolivia.controller;

import com.dynasty.bolivia.model.User;
import com.dynasty.bolivia.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/me")
    public User me(Authentication auth) {
        return userRepository.findByEmail(auth.getName()).orElseThrow();
    }
}
