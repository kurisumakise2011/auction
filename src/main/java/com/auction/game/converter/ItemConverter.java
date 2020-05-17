package com.auction.game.converter;

import com.auction.game.entity.ItemEntity;
import com.auction.game.model.Item;
import com.auction.game.web.ItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ItemConverter {

    @Autowired
    private ItemMediaConverter itemMediaConverter;

    public Item toItemFromDto(ItemDto itemDto) {
        if (itemDto == null) {
            return null;
        }

        Item item = new Item();
        item.setDescription(itemDto.getDescription());
        item.setHidden(itemDto.isHidden());
        item.setPrice(itemDto.getPrice());
        item.setPublished(itemDto.getPublished());
        item.setTitle(itemDto.getTitle());
        item.setMedias(itemDto.getMedias());

        return item;
    }

    public Item toItemFromEntity(ItemEntity entity) {
        if (entity == null) {
            return null;
        }

        Item item = new Item();
        item.setMedias(entity.getMedias().stream().map(media -> itemMediaConverter.toItemMediaFromEntity(media)).collect(Collectors.toList()));
        item.setTitle(entity.getTitle());
        item.setPublished(entity.getPublished());
        item.setPrice(entity.getPrice());
        item.setHidden(entity.getHidden());
        item.setId(entity.getId());
        item.setDescription(entity.getDescription());

        return item;
    }

    public ItemDto toItemDto(Item item) {
        if (item == null) {
            return null;
        }

        ItemDto dto = new ItemDto();
        dto.setDescription(item.getDescription());
        dto.setHidden(item.isHidden());
        dto.setId(item.getId());
        dto.setPrice(item.getPrice());
        dto.setTitle(item.getTitle());
        dto.setId(item.getId());
        dto.setPublished(item.getPublished());

        return dto;
    }

    public ItemEntity toItemEntity(Item item) {
        if (item == null) {
            return null;
        }

        ItemEntity entity = new ItemEntity();
        entity.setDescription(item.getDescription());
        entity.setHidden(item.isHidden());
        entity.setId(item.getId());
        entity.setTitle(item.getTitle());
        entity.setPublished(item.getPublished());
        entity.setPrice(item.getPrice());
        entity.setMedias(item.getMedias().stream().map(media -> itemMediaConverter.toItemMediaEntity(media)).collect(Collectors.toList()));

        return entity;
    }

}
