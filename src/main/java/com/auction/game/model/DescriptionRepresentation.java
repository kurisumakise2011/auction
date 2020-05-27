package com.auction.game.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DescriptionRepresentation {
    @NotBlank
    private String description;
}
