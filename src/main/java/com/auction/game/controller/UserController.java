package com.auction.game.controller;

import com.auction.game.model.UserProfile;
import com.auction.game.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RequestMapping(path = "/users")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/who")
    public UserProfile whoAmI(@NotBlank String id) {
        return userService.getUserById(id);
    }

    @PostMapping(path = "/registration")
    public UserProfile createNewUser(@Valid UserProfile profile) {
        return userService.registration(profile);
    }
}
