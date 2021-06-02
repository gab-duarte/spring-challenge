package com.example.desafiospring.controller;

import com.example.desafiospring.service.SetupService;
import com.example.desafiospring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    SetupService setupService;

    @GetMapping(path = "/setup")
    public ResponseEntity<?> setup(){
        return setupService.setup();
    }

    @PostMapping("/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<?> followSeller(@PathVariable Long userId, @PathVariable Long userIdToFollow) {
        return userService.clientFollowSeller(userId, userIdToFollow);
    }
}