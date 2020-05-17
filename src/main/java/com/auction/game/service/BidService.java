package com.auction.game.service;

import com.auction.game.model.Bid;
import com.auction.game.web.BidFilter;

import java.util.List;

public interface BidService {

    Bid createBid(Bid bid, String userId, String auctionId);

    Bid updateBid(Bid bid, String userId);

    List<Bid> getAllBids(BidFilter filter, String userId);

    List<Bid> getAuctionBids(String auctionBid);

    Bid deleteBid(String id, String userId);

    Bid getBidById(String id, String userId);

}
