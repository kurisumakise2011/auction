package com.auction.game.repository;

import com.auction.game.entity.ProfileSettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileSettingsRepository extends JpaRepository<ProfileSettingsEntity, String> {
}
