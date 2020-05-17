package com.auction.game.model;

import lombok.Data;

import java.util.List;

@Data
public class Auctioneer {
    private String id;
    private String userId;
    private List<Bid> bids;
}
