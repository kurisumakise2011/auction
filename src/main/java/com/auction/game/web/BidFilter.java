package com.auction.game.web;

import lombok.Data;


import java.sql.Timestamp;

@Data
public class BidFilter {
    private Timestamp created;
    private Double price;
    private boolean won;
}
