package com.auction.game.controller;

import com.auction.game.web.ItemDto;
import com.auction.game.web.ItemFilter;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/items")
@RestController
public class ItemController {
    @GetMapping("/items/{id}")
    public ItemDto getItemById(@PathVariable("id") String id) {
        return null;
    }

    @PostMapping("/items")
    public ItemDto createItem(@RequestBody ItemDto itemDto) {
        return null;
    }

    @GetMapping("/items")
    public List<ItemDto> getItems(@RequestBody ItemFilter filter) {
        return null;
    }

    @PutMapping("/items/{id}")
    public ItemDto updateItem(@RequestBody ItemDto itemDto, @PathVariable String id) {
        return null;
    }

    @DeleteMapping("/items/{id}")
    public ItemDto deleteItem(@PathVariable String id) {
        return null;
    }

}
