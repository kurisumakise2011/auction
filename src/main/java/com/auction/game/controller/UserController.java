package com.auction.game.controller;

import com.auction.game.model.DescriptionRepresentation;
import com.auction.game.model.UserProfile;
import com.auction.game.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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

    @GetMapping(path = "{id}")
    public UserProfile getUserInfo(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PostMapping(path = "/registration")
    public UserProfile createNewUser(@Valid @RequestBody UserProfile profile) {
        return userService.registration(profile);
    }

    @PutMapping(path = "/description")
    @ResponseStatus(code = HttpStatus.OK)
    public void updateDescription(@Valid  @RequestBody DescriptionRepresentation description) {
        userService.updateDescription(description.getDescription(), id());
    }

    @PutMapping
    @ResponseStatus(code = HttpStatus.OK)
    public void updateUser(@NotNull byte[] image) {
        userService.updateImage(id(), image);
    }

    @GetMapping(value = "/avatar", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public byte[] getUserImage() {
        return userService.getImage(id());
    }

    @GetMapping(value = "/avatar/{userId}", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public byte[] getUserImage(@PathVariable String userId) {
        return userService.getImage(userId);
    }
}
