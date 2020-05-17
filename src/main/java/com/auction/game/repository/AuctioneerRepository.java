package com.auction.game.repository;

import com.auction.game.entity.AuctioneerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctioneerRepository extends JpaRepository<AuctioneerEntity, String> {
}
