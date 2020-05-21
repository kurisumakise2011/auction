package com.auction.game.web;

import lombok.Data;


import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
public class BidDto {
    private String id;
    private Integer order;
    private Timestamp created;
    @NotNull
    private Double price;
    private String author;
    private boolean won;
}
