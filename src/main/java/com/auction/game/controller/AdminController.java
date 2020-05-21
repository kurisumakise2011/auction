package com.auction.game.controller;

import com.auction.game.converter.AuctionConverter;
import com.auction.game.model.UserProfile;
import com.auction.game.model.UserRole;
import com.auction.game.service.AuctionService;
import com.auction.game.service.UserService;
import com.auction.game.web.AuctionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/admin")
@RestController
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private AuctionConverter auctionConverter;

    @GetMapping("/users")
    public List<UserProfile> userProfiles() {
        return userService.users().stream()
                .filter(user -> user.getSettings().getRole() != UserRole.ADMIN)
                .collect(Collectors.toList());
    }

    @GetMapping("/auctions")
    public List<AuctionDto> auctions() {
        return auctionService.auctions().stream().map(auction -> auctionConverter.toAuctionDto(auction)).collect(Collectors.toList());
    }

    @ResponseStatus(code = HttpStatus.OK)
    @PutMapping("/users/{id}/ban")
    public void banUser(@PathVariable String id) {
        userService.banUser(true, id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @PutMapping("/users/{id}/unban")
    public void unbanUser(@PathVariable String id) {
        userService.banUser(false, id);
    }


    @ResponseStatus(code = HttpStatus.OK)
    @PutMapping("/auctions/{id}/abandon")
    public void abandon(@PathVariable String id) {
        auctionService.abandonAuction(id);
    }

}
