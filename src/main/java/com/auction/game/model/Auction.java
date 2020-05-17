package com.auction.game.model;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class Auction {
    private String id;
    private Timestamp started;
    private Timestamp end;
    private Timestamp updated;
    private AuctionStatus status;
    private Item item;
    private List<Bid> bids;
}
