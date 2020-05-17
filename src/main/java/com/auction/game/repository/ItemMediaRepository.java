package com.auction.game.repository;

import com.auction.game.entity.ItemMediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemMediaRepository extends JpaRepository<ItemMediaEntity, String> {

    @Query("from ItemMediaEntity im where im.category = 'SLIDER'")
    List<ItemMediaEntity> getSliders();

}
