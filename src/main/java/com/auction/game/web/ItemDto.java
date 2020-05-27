package com.auction.game.web;

import com.auction.game.model.ItemMedia;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Data
public class ItemDto {
    private String id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotNull
    private Double price;
    @NotEmpty
    private List<ItemMedia> medias;
    private Timestamp published;
    private boolean hidden;
    private List<String> genre;
    private List<String> material;
    private List<String> category;
    private String author;
    private String authorId;
}
