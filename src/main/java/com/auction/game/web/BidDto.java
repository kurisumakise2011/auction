package com.auction.game.web;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class BidDto {
    private String id;
    private Integer order;
    private Timestamp created;
    private BigDecimal price;
    private boolean won;
}
