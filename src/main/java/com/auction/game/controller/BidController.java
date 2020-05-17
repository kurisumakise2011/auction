package com.auction.game.controller;

import com.auction.game.converter.BidConverter;
import com.auction.game.service.BidService;
import com.auction.game.web.BidDto;
import com.auction.game.web.BidFilter;
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

import static com.auction.game.controller.TokenController.id;

@RestController
public class BidController {
    @Autowired
    private BidConverter bidConverter;

    @Autowired
    private BidService bidService;

    @GetMapping("/bids/{id}")
    public BidDto getBidById(@PathVariable("id") String id) {
        return bidConverter.toBidDto(bidService.getBidById(id, id()));
    }

    @PostMapping("/bids/{auctionId}")
    public BidDto createBid(@RequestBody BidDto bidDto, @PathVariable String auctionId) {
        return bidConverter.toBidDto(bidService.createBid(bidConverter.toBidFromDto(bidDto), id(), auctionId));
    }

    @GetMapping("/bids")
    public List<BidDto> getBids(@RequestBody BidFilter filter) {
        return bidService.getAllBids(filter, id()).stream().map(bid -> bidConverter.toBidDto(bid)).collect(Collectors.toList());
    }

    @GetMapping("/bids/auctions/{id}")
    public List<BidDto> getAuctionBids(@PathVariable String id) {
        return bidService.getAuctionBids(id).stream().map(bid -> bidConverter.toBidDto(bid)).collect(Collectors.toList());
    }

    @PutMapping("/bids/{id}")
    public BidDto updateBid(@RequestBody BidDto bidDto, @PathVariable String id) {
        return bidConverter.toBidDto(bidService.updateBid(bidConverter.toBidFromDto(bidDto), id()));
    }

    @DeleteMapping("/bids/{id}")
    public BidDto deleteBid(@PathVariable String id) {
        return bidConverter.toBidDto(bidService.deleteBid(id, id()));
    }


}
