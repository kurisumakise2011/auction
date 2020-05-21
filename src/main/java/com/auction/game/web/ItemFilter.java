package com.auction.game.web;

import lombok.Data;


import java.sql.Timestamp;

@Data
public class ItemFilter {
    private String title;
    private Double price;
    private Timestamp from;
    private Timestamp to;
    private boolean hidden;
    private boolean onlyMy;
}
