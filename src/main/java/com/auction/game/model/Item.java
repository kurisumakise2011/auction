package com.auction.game.model;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
public class Item {
    private String id;
    private String title;
    private String description;
    private BigDecimal price;
    private List<ItemMedia> medias;
    private Timestamp published;
    private boolean hidden;
}
