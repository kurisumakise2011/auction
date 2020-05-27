package com.auction.game.web;

import lombok.Data;


import java.sql.Timestamp;
import java.util.List;

@Data
public class ItemFilter {
    private String title;
    private Double price;
    private Timestamp from;
    private Timestamp to;
    private boolean hidden;
    private boolean onlyMy;
    private List<String> categories;
    private List<String> genres;
    private List<String> materials;
}
