package com.auction.game.web;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class BidFilter {
    private Timestamp created;
    private BigDecimal price;
    private boolean won;
}
