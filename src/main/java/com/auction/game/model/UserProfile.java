package com.auction.game.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static com.auction.game.controller.ApplicationController.ANONYMOUS;

@Data
public class UserProfile {
    public static final UserProfile ANON = new UserProfile();
    static {
        ANON.setId(ANONYMOUS);
        ANON.setEmail("");
        ANON.setUsername("anon");

        ProfileSettings anonSettings = new ProfileSettings();
        anonSettings.setRole(UserRole.ANONYMOUS);
        ANON.setSettings(anonSettings);
    }

    private String id;

    @NotBlank
    private String username;

    @Email
    private String email;

    @Valid
    private ProfileSettings settings;

    @Valid
    private Credential credential;
}
