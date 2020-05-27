package com.auction.game.converter;

import com.auction.game.entity.ItemEntity;
import com.auction.game.model.Item;
import com.auction.game.web.ItemDto;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
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
        if (StringUtils.isNotBlank(itemDto.getId())) {
            item.setId(itemDto.getId());
        }
        item.setDescription(itemDto.getDescription());
        item.setHidden(itemDto.isHidden());
        item.setPrice(itemDto.getPrice());
        item.setPublished(itemDto.getPublished());
        item.setTitle(itemDto.getTitle());
        item.setMedias(itemDto.getMedias());
        item.setGenre(itemDto.getGenre());
        item.setMaterial(itemDto.getMaterial());
        item.setCategory(itemDto.getCategory());

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
        item.setGenre(Optional.ofNullable(entity.getGenre()).map(s -> Arrays.asList(s.split(","))).orElse(Collections.emptyList()));
        item.setMaterial(Optional.ofNullable(entity.getMaterial()).map(s -> Arrays.asList(s.split(","))).orElse(Collections.emptyList()));
        item.setCategory(Optional.ofNullable(entity.getCategory()).map(s -> Arrays.asList(s.split(","))).orElse(Collections.emptyList()));

        return item;
    }

    public Item withAuthor(Item item, String authorId, String author) {
        if (item == null) {
            return null;
        }

        item.setAuthor(author);
        item.setAuthorId(authorId);

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
        dto.setMedias(item.getMedias());
        dto.setCategory(item.getCategory());
        dto.setGenre(item.getGenre());
        dto.setMaterial(item.getMaterial());
        dto.setAuthor(item.getAuthor());
        dto.setAuthorId(item.getAuthorId());

        return dto;
    }

    public ItemEntity toItemEntity(Item item) {
        if (item == null) {
            return null;
        }

        ItemEntity entity = new ItemEntity();
        entity.setDescription(item.getDescription());
        entity.setHidden(item.isHidden());
        if (StringUtils.isNotBlank(item.getId())) {
            entity.setId(item.getId());
        }
        entity.setTitle(item.getTitle());
        entity.setPublished(item.getPublished());
        entity.setPrice(item.getPrice());
        entity.setMaterial(String.join(",", CollectionUtils.emptyIfNull(item.getMaterial())));
        entity.setGenre(String.join(",", CollectionUtils.emptyIfNull(item.getGenre())));
        entity.setCategory(String.join(",", CollectionUtils.emptyIfNull(item.getCategory())));
        entity.setMedias(item.getMedias().stream().map(media -> itemMediaConverter.toItemMediaEntity(media)).collect(Collectors.toList()));

        return entity;
    }

}
