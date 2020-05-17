package com.auction.game.model;

import lombok.Data;

@Data
public class Credential {
    private String algo;
    private String password;
    private Boolean expired;
}
