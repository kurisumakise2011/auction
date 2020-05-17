package com.auction.game.repository;

import com.auction.game.entity.CredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialRepository extends JpaRepository<CredentialEntity, String> {
}
