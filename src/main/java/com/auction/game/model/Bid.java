package com.auction.game.model;

import lombok.Data;


import java.sql.Timestamp;

@Data
public class Bid {
    private String id;
    private Integer order;
    private Timestamp created;
    private Double price;
    private String author;
    private boolean won;
}
