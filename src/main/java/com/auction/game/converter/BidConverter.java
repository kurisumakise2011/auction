package com.auction.game.converter;

import com.auction.game.entity.BidEntity;
import com.auction.game.model.Bid;
import com.auction.game.web.BidDto;
import org.springframework.stereotype.Component;

@Component
public class BidConverter {

    public BidDto toBidDto(Bid bid) {
        if (bid == null) {
            return null;
        }

        BidDto dto = new BidDto();
        dto.setId(bid.getId());
        dto.setCreated(bid.getCreated());
        dto.setOrder(bid.getOrder());
        dto.setPrice(bid.getPrice());
        dto.setWon(bid.isWon());

        return dto;
    }

    public Bid toBidFromDto(BidDto dto) {
        if (dto == null) {
            return null;
        }

        Bid bid = new Bid();
        bid.setCreated(dto.getCreated());
        bid.setId(dto.getId());
        bid.setOrder(dto.getOrder());
        bid.setWon(dto.isWon());
        bid.setPrice(dto.getPrice());

        return bid;
    }

    public Bid toBidFromEntity(BidEntity entity) {
        if (entity == null) {
            return null;
        }

        Bid bid = new Bid();
        bid.setCreated(entity.getCreated());
        bid.setId(entity.getId());
        bid.setOrder(entity.getOrder());
        bid.setWon(entity.getWon());
        bid.setPrice(entity.getPrice());

        return bid;
    }

    public BidEntity toBidEntity(Bid bid) {
        if (bid == null) {
            return null;
        }

        BidEntity entity = new BidEntity();
        entity.setPrice(bid.getPrice());
        entity.setId(bid.getId());
        entity.setCreated(bid.getCreated());
        entity.setOrder(bid.getOrder());
        entity.setWon(bid.isWon());

        return entity;
    }

}
