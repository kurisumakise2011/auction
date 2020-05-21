package com.auction.game.service;

import com.auction.game.converter.AuctionConverter;
import com.auction.game.entity.AuctionEntity;
import com.auction.game.entity.AuctioneerEntity;
import com.auction.game.entity.ItemEntity;
import com.auction.game.exception.NotFoundSuchEntityException;
import com.auction.game.exception.UnknownUserException;
import com.auction.game.exception.UnprocessableEntityException;
import com.auction.game.model.Auction;
import com.auction.game.model.AuctionStatus;
import com.auction.game.repository.AuctionRepository;
import com.auction.game.repository.AuctioneerRepository;
import com.auction.game.repository.ItemRepository;
import com.auction.game.web.AuctionFilter;
import com.auction.game.web.AuctionRole;
import com.auction.game.web.ItemFilter;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@Transactional
public class AuctionServiceImpl implements AuctionService {
    @Autowired
    private AuctioneerRepository auctioneerRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private AuctionConverter auctionConverter;

    @Override
    public Auction createAuction(Auction auction, String userId) {
        AuctioneerEntity auctioneer = auctioneerRepository.findById(userId).orElse(null);
        if (auctioneer == null) {
            throw new UnknownUserException("Unknown user");
        }

        ItemEntity itemEntity = itemRepository.findById(userId, auction.getItem().getId());

        if (itemEntity == null) {
            throw new NotFoundSuchEntityException("No such item");
        }

        if (BooleanUtils.isFalse(itemEntity.getHidden())) {
            throw new UnprocessableEntityException("Item already on auction");
        }
        itemEntity.setHidden(false);

        AuctionEntity auctionEntity = new AuctionEntity();
        auctionEntity.setEnd(auction.getEnd());
        auctionEntity.setAuthor(auctioneer);
        auctionEntity.setAuctionItemEntity(itemEntity);
        auctionEntity.setStatus(AuctionStatus.ACTIVE);
        auctionEntity.setAuctionItemEntity(itemEntity);

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
        return resolveAuctions(filter, userId)
                .stream()
                .filter(getStatusFilter(filter))
                .filter(getTitlePredicate(filter))
                .filter(getPricePredicate(filter))
                .filter(getFromPredicate(filter))
                .filter(getToPredicate(filter))
                .collect(Collectors.toList());
    }

    private Predicate<Auction> getStatusFilter(AuctionFilter filter) {
        return ObjectUtils.isNotEmpty(filter.getStatus()) && filter.getStatus() != AuctionStatus.ALL
                ? auction -> auction.getStatus().equals(filter.getStatus())
                : auction -> true;
    }

    private Predicate<Auction> getTitlePredicate(AuctionFilter filter) {
        return StringUtils.isNotBlank(Optional.ofNullable(filter.getItem())
                .map(ItemFilter::getTitle)
                .orElse(null))
                ? auction -> {
                        String actual = auction.getItem().getTitle();
                        String expected = filter.getItem().getTitle();
                        return actual.contains(expected) || actual.startsWith(expected) || actual.endsWith(expected);
                }
                : auction -> true;
    }

    private Predicate<Auction> getPricePredicate(AuctionFilter filter) {
        return ObjectUtils.isNotEmpty(Optional.ofNullable(filter.getItem()).map(ItemFilter::getPrice).orElse(null))
                ? auction -> {
                        Double actual = auction.getItem().getPrice();
                        Double expected = filter.getItem().getPrice();
                        return Double.compare(expected, actual) >= 0;
                }
                : auction -> true;
    }

    private Predicate<Auction> getFromPredicate(AuctionFilter filter) {
        return ObjectUtils.isNotEmpty(filter.getStarted())
                ? auction -> {
                        Timestamp actual = auction.getStarted();
                        Timestamp expected = filter.getStarted();
                        return actual.after(expected) || actual.equals(expected);
                }
                : auction -> true;
    }

    private Predicate<Auction> getToPredicate(AuctionFilter filter) {
        return ObjectUtils.isNotEmpty(filter.getEnd())
                ? auction -> {
                    Timestamp actual = auction.getEnd();
                    Timestamp expected = filter.getEnd();
                    return actual.after(expected) || actual.equals(expected);
                }
                : auction -> true;
    }

    private List<Auction> resolveAuctions(AuctionFilter filter, String userId) {
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

    @Override
    public boolean owner(String id, String userId) {
        return auctionRepository.isUserOwner(id, userId);
    }

    @Override
    public void abandonAuction(String auctionId) {
        auctionRepository.abandonAuction(auctionId);
    }

    @Override
    public List<Auction> auctions() {
        return auctionRepository.findAll().stream().map(auction -> auctionConverter.toAuctionFromEntity(auction)).collect(Collectors.toList());
    }
}
