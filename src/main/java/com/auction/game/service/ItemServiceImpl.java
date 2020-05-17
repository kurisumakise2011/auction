package com.auction.game.service;

import com.auction.game.converter.ItemConverter;
import com.auction.game.entity.AuctioneerEntity;
import com.auction.game.entity.ItemEntity;
import com.auction.game.entity.UserProfileEntity;
import com.auction.game.exception.NotFoundSuchEntityException;
import com.auction.game.exception.UnknownUserException;
import com.auction.game.model.Item;
import com.auction.game.repository.ItemRepository;
import com.auction.game.repository.UserProfileRepository;
import com.auction.game.web.ItemFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Transactional
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemConverter itemConverter;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public Item createItem(Item item, String userId) {
        ItemEntity entity = itemConverter.toItemEntity(item);

        UserProfileEntity userProfileEntity = userProfileRepository.findById(userId).orElse(null);
        if (userProfileEntity == null) {
            throw new UnknownUserException("Unknown user");
        }
        entity.setHolder(userProfileEntity.getAuctioneerEntity());
        ItemEntity save = itemRepository.save(entity);

        return itemConverter.toItemFromEntity(save);
    }

    @Override
    public Item updateItem(Item item, String userId) {
        ItemEntity entity = itemConverter.toItemEntity(item);

        UserProfileEntity userProfileEntity = userProfileRepository.findById(userId).orElse(null);
        if (userProfileEntity == null) {
            throw new UnknownUserException("Unknown user");
        }

        AuctioneerEntity auctioneerEntity = userProfileEntity.getAuctioneerEntity();

        if (auctioneerEntity.getItems().contains(entity)) {
            ItemEntity save = itemRepository.save(entity);
            return itemConverter.toItemFromEntity(save);
        }

        throw new NotFoundSuchEntityException("user does not hold a item " + item.getId() + " update is impossible");
    }

    @Override
    public List<Item> getAllItems(ItemFilter filter, String userId) {
        Stream<ItemEntity> items;
        if (filter.isOnlyMy()) {
            if (StringUtils.isBlank(filter.getTitle())) {
                items = itemRepository.findItemsByTitle(userId, filter.getTitle()).stream();
            } else {
                items = itemRepository.myItems(userId).stream();
            }
        } else {
            if (StringUtils.isBlank(filter.getTitle())) {
                items = itemRepository.findItemsByTitle(filter.getTitle()).stream();
            } else {
                items = itemRepository.findAll().stream();
            }
        }

        items.filter(item -> {
            Timestamp published = item.getPublished();
            if (filter.getFrom() != null) {
                return published.after(filter.getFrom()) || filter.getFrom().equals(published);
            } else {
                return false;
            }
        }).filter(item -> {
            Timestamp published = item.getPublished();
            if (filter.getTo() != null) {
                return published.before(filter.getTo()) || filter.getTo().equals(published);
            } else {
                return false;
            }
        });

        return items.map(entity -> itemConverter.toItemFromEntity(entity)).collect(Collectors.toList());
    }

    @Override
    public Item deleteItem(String id, String userId) {
        Item itemById = getItemById(id, userId);
        if (itemById == null) {
            throw new NotFoundSuchEntityException("Item not found");
        }
        itemRepository.deleteById(id);
        return itemById;
    }

    @Override
    public Item getItemById(String id, String userId) {
        ItemEntity byId = itemRepository.findById(userId, id);
        if (byId == null) {
            throw new NotFoundSuchEntityException("Item not found");
        }
        return itemConverter.toItemFromEntity(byId);
    }

}
