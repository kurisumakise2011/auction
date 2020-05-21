package com.auction.game.repository;

import com.auction.game.entity.AuctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuctionRepository extends JpaRepository<AuctionEntity, String> {

    @Query("select count(e) > 0 from AuctionEntity e join e.author a where e.id = ?1 and a.id = ?2")
    boolean isUserOwner(String id, String userId);

    @Modifying
    @Query("update AuctionEntity e set e.status = 'ABANDONED' where e.id = ?1")
    void abandonAuction(String auctionId);

    @Query("from AuctionEntity e where e.status = 'ACTIVE'")
    List<AuctionEntity> findAllByStatus();

}
