package com.auction.game.controller;

import com.auction.game.converter.ItemMediaConverter;
import com.auction.game.model.ItemMedia;
import com.auction.game.repository.ItemMediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping(name = "/resources")
@Controller
public class StaticResourceController {

    @Autowired
    private ItemMediaRepository mediaRepository;

    @Autowired
    private ItemMediaConverter itemMediaConverter;

    @GetMapping(name = "/sliders")
    public List<ItemMedia> sliderImages() {
        return mediaRepository.getSliders().stream()
                .map(media -> itemMediaConverter.toItemMediaFromEntity(media))
                .collect(Collectors.toList());
    }

}
