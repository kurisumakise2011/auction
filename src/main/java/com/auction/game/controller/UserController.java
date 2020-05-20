package com.auction.game.controller;

import com.auction.game.model.UserProfile;
import com.auction.game.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.auction.game.controller.ApplicationController.ANONYMOUS;
import static com.auction.game.controller.ApplicationController.id;

@RequestMapping("/users")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/who")
    public UserProfile whoAmI() {
        String id = id();
        if (ANONYMOUS.equals(id)) {
            return UserProfile.ANON;
        }
        return userService.getUserById(id);
    }

    @PostMapping(path = "/registration")
    public UserProfile createNewUser(@Valid @RequestBody UserProfile profile) {
        return userService.registration(profile);
    }
}
