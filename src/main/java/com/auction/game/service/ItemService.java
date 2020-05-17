package com.auction.game.service;

import com.auction.game.model.Item;
import com.auction.game.web.ItemFilter;

import java.util.List;

public interface ItemService {

    Item createItem(Item item, String userId);

    Item updateItem(Item item, String userId);

    List<Item> getAllItems(ItemFilter filter, String userId);

    Item deleteItem(String id, String userId);

    Item getItemById(String id, String userId);
    
}
