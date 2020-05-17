package com.auction.game.model;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class Bid {
    private String id;
    private Integer order;
    private Timestamp created;
    private BigDecimal price;
    private boolean won;
}
