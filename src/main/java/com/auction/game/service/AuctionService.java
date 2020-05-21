package com.auction.game.service;

import com.auction.game.model.Auction;
import com.auction.game.web.AuctionFilter;

import java.util.List;

public interface AuctionService {

    Auction createAuction(Auction auction, String userId);

    Auction updateAuction(Auction auction, String userId);

    List<Auction> getAllAuctions(AuctionFilter filter, String userId);

    Auction deleteAuction(String id, String userId);

    Auction getAuctionById(String id);

    boolean owner(String id, String userId);

    void abandonAuction(String auctionId);

    List<Auction> auctions();
}
