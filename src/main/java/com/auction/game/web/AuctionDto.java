package com.auction.game.web;

import com.auction.game.model.AuctionStatus;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class AuctionDto {
    private String id;
    private Timestamp started;
    private Timestamp end;
    private Timestamp updated;
    private AuctionStatus status;
    private ItemDto item;
    private List<BidDto> bids;
    private Double currentPrice;
}
