package com.auction.game.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Credential {
    private String algo = "MD5";
    @NotBlank
    private String password;
    private Boolean expired = false;
}
