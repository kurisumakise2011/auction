package com.auction.game.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorModel {
    private Integer status;
    private String message;
    private String requestURI;
    private String title;
    private LocalDateTime time = LocalDateTime.now();
}
