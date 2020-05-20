package com.auction.game.converter;

import com.auction.game.entity.AuctionEntity;
import com.auction.game.model.Auction;
import com.auction.game.model.Bid;
import com.auction.game.web.AuctionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.stream.Collectors;

@Component
public class AuctionConverter {

    @Autowired
    private ItemConverter itemConverter;

    @Autowired
    private BidConverter bidConverter;

    public Auction toAuctionFromDto(AuctionDto auctionDto) {
        if (auctionDto == null) {
            return null;
        }

        Auction auction = new Auction();
        auction.setStatus(auctionDto.getStatus());
        auction.setEnd(auctionDto.getEnd());
        auction.setId(auctionDto.getId());
        auction.setStarted(auctionDto.getStarted());
        auction.setUpdated(auctionDto.getUpdated());
        auction.setItem(itemConverter.toItemFromDto(auctionDto.getItem()));
        auction.setBids(auctionDto.getBids().stream().map(bid -> bidConverter.toBidFromDto(bid)).collect(Collectors.toList()));

        return auction;
    }

    public Auction toAuctionFromEntity(AuctionEntity entity) {
        if (entity == null) {
            return null;
        }

        Auction auction = new Auction();
        auction.setStatus(entity.getStatus());
        auction.setEnd(entity.getEnd());
        auction.setId(entity.getId());
        auction.setStarted(entity.getStarted());
        auction.setUpdated(entity.getUpdated());
        auction.setItem(itemConverter.toItemFromEntity(entity.getAuctionItemEntity()));
        auction.setBids(entity.getBids().stream().map(bid -> bidConverter.toBidFromEntity(bid)).collect(Collectors.toList()));

        return auction;
    }

    public AuctionDto toAuctionDto(Auction auction) {
        if (auction == null) {
            return null;
        }

        AuctionDto dto = new AuctionDto();
        dto.setEnd(auction.getEnd());
        dto.setId(auction.getId());
        dto.setStarted(auction.getStarted());
        dto.setStatus(auction.getStatus());
        dto.setUpdated(auction.getUpdated());
        dto.setItem(itemConverter.toItemDto(auction.getItem()));
        dto.setBids(auction.getBids().stream().map(bid -> bidConverter.toBidDto(bid)).collect(Collectors.toList()));
        dto.setCurrentPrice(auction.getBids().stream().max(Comparator.comparing(Bid::getPrice)).map(Bid::getPrice).orElse(auction.getItem().getPrice()));

        return dto;
    }

    public AuctionEntity toAuctionEntity(Auction auction) {
        if (auction == null) {
            return null;
        }

        AuctionEntity entity = new AuctionEntity();
        entity.setStatus(auction.getStatus());
        entity.setEnd(auction.getEnd());
        entity.setId(auction.getId());
        entity.setStarted(auction.getStarted());
        entity.setUpdated(auction.getUpdated());
        entity.setAuctionItemEntity(itemConverter.toItemEntity(auction.getItem()));
        entity.setBids(auction.getBids().stream().map(bid -> bidConverter.toBidEntity(bid)).collect(Collectors.toList()));

        return entity;
    }

}
