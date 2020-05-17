package com.auction.game.service;

import com.auction.game.converter.AuctionConverter;
import com.auction.game.entity.AuctionEntity;
import com.auction.game.entity.AuctioneerEntity;
import com.auction.game.exception.NotFoundSuchEntityException;
import com.auction.game.exception.UnknownUserException;
import com.auction.game.model.Auction;
import com.auction.game.repository.AuctionRepository;
import com.auction.game.repository.AuctioneerRepository;
import com.auction.game.web.AuctionFilter;
import com.auction.game.web.AuctionRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class AuctionServiceImpl implements AuctionService {
    @Autowired
    private AuctioneerRepository auctioneerRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private AuctionConverter auctionConverter;

    @Override
    public Auction createAuction(Auction auction, String userId) {
        AuctioneerEntity auctioneer = auctioneerRepository.findById(userId).orElse(null);
        if (auctioneer == null) {
            throw new UnknownUserException("Unknown user");
        }

        AuctionEntity auctionEntity = auctionConverter.toAuctionEntity(auction);
        auctionEntity.setAuthor(auctioneer);

        return auctionConverter.toAuctionFromEntity(auctionRepository.save(auctionEntity));
    }

    @Override
    public Auction updateAuction(Auction auction, String userId) {
        AuctioneerEntity auctioneer = auctioneerRepository.findById(userId).orElse(null);
        if (auctioneer == null) {
            throw new UnknownUserException("Unknown user");
        }

        AuctionEntity auctionEntity = auctionRepository.findById(auction.getId()).orElse(null);
        if (auctionEntity == null) {
            throw new NotFoundSuchEntityException("No such action " + auction.getId());
        }

        if (auctioneer.getAuthor().contains(auctionEntity)) {
            return auctionConverter.toAuctionFromEntity(auctionRepository.save(auctionConverter.toAuctionEntity(auction)));
        }

        throw new NotFoundSuchEntityException("user does not hold an auction " + auction.getId() + " update is impossible");
    }

    @Override
    public List<Auction> getAllAuctions(AuctionFilter filter, String userId) {
        AuctioneerEntity auctioneer = auctioneerRepository.findById(userId).orElse(null);
        if (auctioneer == null) {
            throw new UnknownUserException("Unknown user");
        }

        if (filter.getRole() == AuctionRole.AUTHOR) {
            return auctioneer.getAuthor().stream().map(auction -> auctionConverter.toAuctionFromEntity(auction)).collect(Collectors.toList());
        } else if (filter.getRole() == AuctionRole.PARTICIPANT) {
            return auctioneer.getTakeParticipant().stream().map(auction -> auctionConverter.toAuctionFromEntity(auction)).collect(Collectors.toList());
        } else if (filter.getRole() == AuctionRole.WINNER) {
            return auctioneer.getWinner().stream().map(auction -> auctionConverter.toAuctionFromEntity(auction)).collect(Collectors.toList());
        } else {
            return auctionRepository.findAll().stream().map(auction -> auctionConverter.toAuctionFromEntity(auction)).collect(Collectors.toList());
        }
    }

    @Override
    public Auction deleteAuction(String id, String userId) {
        AuctioneerEntity auctioneer = auctioneerRepository.findById(userId).orElse(null);
        if (auctioneer == null) {
            throw new UnknownUserException("Unknown user");
        }

        Auction auctionById = getAuctionById(id);
        if (auctionById == null) {
            throw new NotFoundSuchEntityException("No such action " + id);
        }

        auctionRepository.deleteById(id);

        return auctionById;
    }

    @Override
    public Auction getAuctionById(String id) {
        AuctionEntity one = auctionRepository.findById(id).orElse(null);
        if (one == null) {
            throw new NotFoundSuchEntityException("No such action " + id);
        }
        return auctionConverter.toAuctionFromEntity(one);
    }
}
