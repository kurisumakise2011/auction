package com.auction.game.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserProfile {
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
