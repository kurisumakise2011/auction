package com.auction.game.service;

import com.auction.game.converter.BidConverter;
import com.auction.game.entity.AuctionEntity;
import com.auction.game.entity.AuctioneerEntity;
import com.auction.game.entity.BidEntity;
import com.auction.game.exception.NotFoundSuchEntityException;
import com.auction.game.exception.UnknownUserException;
import com.auction.game.exception.UnprocessableEntityException;
import com.auction.game.model.AuctionStatus;
import com.auction.game.model.Bid;
import com.auction.game.repository.AuctionRepository;
import com.auction.game.repository.AuctioneerRepository;
import com.auction.game.repository.BidRepository;
import com.auction.game.web.BidFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Transactional
public class BidServiceImpl implements BidService {

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private BidConverter bidConverter;

    @Autowired
    private AuctioneerRepository auctioneerRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Override
    public Bid createBid(Bid bid, String userId, String auctionId) {
        AuctioneerEntity auctioneer = auctioneerRepository.findById(userId).orElse(null);
        if (auctioneer == null) {
            throw new UnknownUserException("Unknown user");
        }

        AuctionEntity auction = auctionRepository.findById(auctionId).orElse(null);

        if (auction == null) {
            throw new NotFoundSuchEntityException("No such auction");
        }

        if (auction.getStatus() != AuctionStatus.ACTIVE) {
            throw new UnprocessableEntityException("Auction is not active");
        }

        BidEntity lastBid = getLastBid(auction);
        Double price = Optional.ofNullable(lastBid).map(BidEntity::getPrice).orElse(auction.getAuctionItemEntity().getPrice());
        if (Double.compare(bid.getPrice(), price) <= 0) {
            throw new UnprocessableEntityException("Bid cannot be lower than current bid price");
        }

        BidEntity entity = bidConverter.toBidEntity(bid);
        if (lastBid != null) {
            entity.setOrder(lastBid.getOrder() + 1);
        } else {
            entity.setOrder(1);
        }

        entity.setAuctioneer(auctioneer);
        entity.setAuctionEntity(auction);

        return bidConverter.toBidFromEntity(bidRepository.save(entity));
    }

    private BidEntity getLastBid(AuctionEntity entity) {
        return entity.getBids()
                .stream()
                .max(Comparator.comparing(BidEntity::getPrice))
                .orElse(null);
    }

    @Override
    public Bid updateBid(Bid bid, String userId) {
        return null;
    }

    @Override
    public List<Bid> getAllBids(BidFilter filter, String userId) {
        AuctioneerEntity auctioneer = auctioneerRepository.findById(userId).orElse(null);
        if (auctioneer == null) {
            throw new UnknownUserException("Unknown user");
        }

        return auctioneer.getBids().stream()
                .map(bid -> bidConverter.toBidFromEntity(bid)).collect(Collectors.toList());
    }

    @Override
    public List<Bid> getAuctionBids(String auctionBid) {
        AuctionEntity auctionEntity = auctionRepository.findById(auctionBid).orElse(null);

        if (auctionEntity == null) {
            throw new NotFoundSuchEntityException("No such auction");
        }

        return auctionEntity.getBids().stream().map(bid -> bidConverter.toBidFromEntity(bid)).collect(Collectors.toList());
    }

    @Override
    public Bid deleteBid(String id, String userId) {
        AuctioneerEntity auctioneer = auctioneerRepository.findById(userId).orElse(null);
        if (auctioneer == null) {
            throw new UnknownUserException("Unknown user");
        }

        Bid bidById = getBidById(id, userId);

        if (bidById == null) {
            throw new NotFoundSuchEntityException("No such bid");
        }

        bidRepository.deleteById(id);
        return bidById;
    }

    @Override
    public Bid getBidById(String id, String userId) {
        return bidConverter.toBidFromEntity(bidRepository.findByIdAndUserId(id, userId));
    }
}
