package com.auction.game.web;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class ItemFilter {
    private String title;
    private BigDecimal price;
    private Timestamp from;
    private Timestamp to;
    private boolean hidden;
    private boolean onlyMy;
}
