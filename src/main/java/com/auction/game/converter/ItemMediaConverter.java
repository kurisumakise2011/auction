package com.auction.game.converter;

import com.auction.game.entity.ItemMediaEntity;
import com.auction.game.model.ItemMedia;
import org.springframework.stereotype.Component;

@Component
public class ItemMediaConverter {

    public ItemMedia toItemMediaFromEntity(ItemMediaEntity entity) {
        if (entity == null) {
            return null;
        }

        ItemMedia itemMedia = new ItemMedia();
        itemMedia.setImageUrl(entity.getUrl());
        itemMedia.setId(entity.getId());
        itemMedia.setCategory(entity.getCategory());
        itemMedia.setMediaType(entity.getMediaType());

        return itemMedia;
    }

    public ItemMediaEntity toItemMediaEntity(ItemMedia item) {
        if (item == null) {
            return null;
        }

        ItemMediaEntity entity = new ItemMediaEntity();
        entity.setUrl(item.getImageUrl());
        entity.setId(item.getId());
        entity.setCategory(item.getCategory());
        entity.setMediaType(item.getMediaType());

        return entity;
    }

}
