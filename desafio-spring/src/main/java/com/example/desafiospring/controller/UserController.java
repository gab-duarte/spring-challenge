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
        return userService.userFollowSeller(userId, userIdToFollow);
    }

    @GetMapping(path = "/{userId}/followers/count")
    public ResponseEntity<?> numberOfSellerFollowers(@PathVariable Long userId){
        return userService.numberOfSellerFollowers(userId);
    }

    @GetMapping(path = "/{userId}/followers/list")
    public ResponseEntity<?> sellerFollowersList(@PathVariable Long userId, @RequestParam(required = false) String order){
        return userService.getFollowersList(userId, order);
    }

    @GetMapping(path = "/{userId}/followed/list")
    public ResponseEntity<?> followedList(@PathVariable Long userId){
        return userService.getSellersFollowedByUsers(userId);
    }

    @PostMapping(path = "/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<?> unfollowSeller(@PathVariable Long userId, @PathVariable Long userIdToUnfollow){
        return userService.userUnfollowSeller(userId, userIdToUnfollow);
    }
}
