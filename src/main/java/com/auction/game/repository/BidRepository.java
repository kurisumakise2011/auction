package com.auction.game.repository;

import com.auction.game.entity.BidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BidRepository extends JpaRepository<BidEntity, String> {

    @Query("from BidEntity b join b.auctioneer a where b.id = ?1 and a.id = ?2")
    BidEntity findByIdAndUserId(String id, String userId);
}
