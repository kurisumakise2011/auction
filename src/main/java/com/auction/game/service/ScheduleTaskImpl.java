package com.auction.game.service;

import com.auction.game.entity.AuctionEntity;
import com.auction.game.entity.AuctioneerEntity;
import com.auction.game.entity.BidEntity;
import com.auction.game.entity.ItemEntity;
import com.auction.game.model.AuctionStatus;
import com.auction.game.repository.AuctionRepository;
import com.auction.game.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ScheduleTaskImpl implements ScheduleTask {
    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Transactional
    @Scheduled(fixedRate = 5000)
    public void updateAuctions() {
        List<AuctionEntity> all = auctionRepository.findAllByStatus();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        if (CollectionUtils.isNotEmpty(all)) {
            for (AuctionEntity entity : all) {
                if (timestamp.after(entity.getEnd())) {
                    log.info("Summarizing auction '{}'", entity.getId());
                    ItemEntity itemEntity = entity.getAuctionItemEntity();
                    BidEntity bid = entity.getBids().stream().max(Comparator.comparing(BidEntity::getPrice)).orElse(null);
                    if (bid == null) {
                        log.info("None put on auction '{}' closing it without result", entity.getId());
                    } else {
                        bid.setWon(Boolean.TRUE);
                        AuctioneerEntity auctioneer = bid.getAuctioneer();
                        entity.setWinner(auctioneer);
                        log.info("Winner is '{}' with bid '{}' and price '{}'", auctioneer.getId(), bid.getId(), bid.getPrice());

                        entity.setParticipants(entity.getBids().stream().map(BidEntity::getAuctioneer).collect(Collectors.toList()));

                        log.info("Changing item '{}' holder to '{}'", itemEntity.getId(), auctioneer.getId());

                        itemEntity.setPrice(bid.getPrice());
                        itemEntity.setHolder(auctioneer);
                    }
                    itemEntity.setHidden(true);
                    entity.setStatus(AuctionStatus.FINISHED);

                    auctionRepository.save(entity);
                }
            }
        } else {
            log.info("There are no active auctions");
        }

    }

}
