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
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
        entity.setHidden(true);
        entity.getMedias().forEach(itemMedia -> itemMedia.setItemEntity(entity));

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
        List<ItemEntity> items;
        if (filter.isOnlyMy()) {
            if (StringUtils.isNotBlank(filter.getTitle())) {
                items = itemRepository.findItemsByTitle(userId, filter.getTitle());
            } else {
                items = itemRepository.myItems(userId);
            }
        } else {
            if (StringUtils.isBlank(filter.getTitle())) {
                items = itemRepository.findItemsByTitle(filter.getTitle());
            } else {
                items = itemRepository.findAll();
            }
        }

        return items.stream().filter(item -> {
            Timestamp published = item.getPublished();
            if (filter.getFrom() != null) {
                return published.after(filter.getFrom()) || filter.getFrom().equals(published);
            } else {
                return true;
            }
        }).filter(item -> {
            Timestamp published = item.getPublished();
            if (filter.getTo() != null) {
                return published.before(filter.getTo()) || filter.getTo().equals(published);
            } else {
                return true;
            }
        }).filter(entity -> {
             if (CollectionUtils.isNotEmpty(filter.getCategories())) {
                 return CollectionUtils.containsAny(filter.getCategories(),
                         Optional.ofNullable(entity.getCategory())
                                 .map(s -> Arrays.asList(s.split(",")))
                                 .orElse(Collections.emptyList()));
             }
             return true;
        }).filter(entity -> {
            if (CollectionUtils.isNotEmpty(filter.getGenres())) {
                return CollectionUtils.containsAny(filter.getGenres(),
                        Optional.ofNullable(entity.getGenre())
                                .map(s -> Arrays.asList(s.split(",")))
                                .orElse(Collections.emptyList()));
            }
            return true;
        }).filter(entity -> {
            if (CollectionUtils.isNotEmpty(filter.getMaterials())) {
                return CollectionUtils.containsAny(filter.getMaterials(),
                        Optional.ofNullable(entity.getMaterial())
                                .map(s -> Arrays.asList(s.split(",")))
                                .orElse(Collections.emptyList()));
            }
            return true;
        }).filter(entity -> Objects.nonNull(entity.getHolder())).map(entity -> {
            Item item = itemConverter.toItemFromEntity(entity);
            UserProfileEntity profile = entity.getHolder().getUserProfile();
            itemConverter.withAuthor(item, profile.getId(), profile.getUsername());

            return item;
        }).collect(Collectors.toList());
    }

    @Override
    public Item deleteItem(String id, String userId) {
        Item itemById = getItemById(userId, id);
        if (itemById == null) {
            throw new NotFoundSuchEntityException("Item not found");
        }
        itemRepository.deleteById(id);
        return itemById;
    }

    @Override
    public Item getItemById(String id) {
        ItemEntity byId = itemRepository.findById(id).orElse(null);
        return getItemById(byId);
    }

    @Override
    public Item getItemById(String userId, String id) {
        ItemEntity byId = itemRepository.findById(userId, id);
        return getItemById(byId);
    }

    private Item getItemById(ItemEntity itemEntity) {
        if (itemEntity == null) {
            throw new NotFoundSuchEntityException("Item not found");
        }
        Item item = itemConverter.toItemFromEntity(itemEntity);
        UserProfileEntity profile = itemEntity.getHolder().getUserProfile();
        itemConverter.withAuthor(item, profile.getId(), profile.getUsername());

        return item;
    }

    @Override
    public List<Item> getAllItems(int limit) {
        return itemRepository.findAllWithLimit(limit)
                .stream()
                 .filter(item -> Objects.nonNull(item.getHolder()))
                .map(item -> {
                    Item model = itemConverter.toItemFromEntity(item);
                    UserProfileEntity profile = item.getHolder().getUserProfile();
                    itemConverter.withAuthor(model, profile.getId(), profile.getUsername());

                    return model;
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean isOwnerOfItem(String userId, String id) {
        return itemRepository.isUserOwner(id, userId);
    }

}
