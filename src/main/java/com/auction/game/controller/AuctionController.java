package com.auction.game.controller;

import com.auction.game.converter.AuctionConverter;
import com.auction.game.service.AuctionService;
import com.auction.game.web.AuctionDto;
import com.auction.game.web.AuctionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.auction.game.controller.ApplicationController.id;

@RequestMapping(path = "/auctions")
@RestController
public class AuctionController {
    @Autowired
    private AuctionService auctionService;

    @Autowired
    private AuctionConverter auctionConverter;

    @GetMapping("/auctions/{id}")
    public AuctionDto getAuctionById(@PathVariable("id") String id) {
        return auctionConverter.toAuctionDto(auctionService.getAuctionById(id));
    }

    @PostMapping("/auctions")
    public AuctionDto createAuction(@RequestBody AuctionDto auction) {
        return auctionConverter.toAuctionDto(
                auctionService.createAuction(auctionConverter.toAuctionFromDto(auction), id()));
    }

    @GetMapping("/auctions")
    public List<AuctionDto> getAuctions(@RequestBody AuctionFilter filter) {
        return auctionService.getAllAuctions(filter, id()).stream().map(auction -> auctionConverter.toAuctionDto(auction)).collect(Collectors.toList());
    }

    @PutMapping("/auctions/{id}")
    public AuctionDto updateAuction(@RequestBody AuctionDto auctionDto, @PathVariable String id) {
        return auctionConverter.toAuctionDto(auctionService.updateAuction(auctionConverter.toAuctionFromDto(auctionDto), id()));
    }

    @DeleteMapping("/auctions/{id}")
    public AuctionDto deleteAuction(@PathVariable String id) {
        return auctionConverter.toAuctionDto(auctionService.deleteAuction(id, id()));
    }

}
