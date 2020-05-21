package com.auction.game.controller;

import com.auction.game.converter.ItemConverter;
import com.auction.game.service.ItemService;
import com.auction.game.web.ItemDto;
import com.auction.game.web.ItemFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.auction.game.controller.ApplicationController.id;

@RestController
public class ItemController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemConverter itemConverter;

    @GetMapping("/items/{id}")
    public ItemDto getItemById(@PathVariable("id") String id) {
        return itemConverter.toItemDto(itemService.getItemById(id, id()));
    }

    @PostMapping("/items")
    public ItemDto createItem(@RequestBody ItemDto itemDto) {
        return itemConverter.toItemDto(itemService.createItem(itemConverter.toItemFromDto(itemDto), id()));
    }

    @PostMapping("/filter/items")
    public List<ItemDto> getItems(@RequestBody ItemFilter filter) {
        return itemService.getAllItems(filter, id()).stream()
                .map(item -> itemConverter.toItemDto(item))
                .collect(Collectors.toList());
    }

    @PutMapping("/items/{id}")
    public ItemDto updateItem(@RequestBody ItemDto itemDto, @PathVariable String id) {
        return itemConverter.toItemDto(itemService.updateItem(itemConverter.toItemFromDto(itemDto), id()));
    }

    @DeleteMapping("/items/{id}")
    public ItemDto deleteItem(@PathVariable String id) {
        return itemConverter.toItemDto(itemService.deleteItem(id, id()));
    }

}
