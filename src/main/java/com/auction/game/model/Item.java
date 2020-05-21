package com.auction.game.model;

import lombok.Data;


import java.sql.Timestamp;
import java.util.List;

@Data
public class Item {
    private String id;
    private String title;
    private String description;
    private Double price;
    private List<ItemMedia> medias;
    private Timestamp published;
    private boolean hidden;
}
