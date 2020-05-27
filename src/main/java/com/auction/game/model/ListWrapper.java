package com.auction.game.model;

import lombok.Data;

import java.util.List;

@Data
public class ListWrapper<T> {
    private List<T> list;
}
