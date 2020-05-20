package com.auction.game.web;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AuctionFilter {
    private Timestamp started;
    private Timestamp end;
    private boolean active = true;
    private ItemFilter item;
    private AuctionRole role = AuctionRole.NONE;
}
