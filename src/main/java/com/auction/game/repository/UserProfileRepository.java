package com.auction.game.repository;

import com.auction.game.entity.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserProfileRepository extends JpaRepository<UserProfileEntity, String> {

    UserProfileEntity findByUsername(String username);

    UserProfileEntity findByEmail(String email);

    @Query("select u.id from UserProfileEntity u where u.username = ?1")
    String findIdByUsername(String username);

    @Modifying
    @Query("update ProfileSettingsEntity s set s.banned = ?1 where s.id = ?2")
    int updateBanned(boolean banned, String id);

}
