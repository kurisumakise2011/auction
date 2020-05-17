package com.auction.game.entity;

import com.auction.game.model.UserRole;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "T_PROFILE_SETTINGS")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class ProfileSettingsEntity {
    @Id
    @Column(nullable = false, updatable = false, unique = true)
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    private Boolean banned;
    private Boolean online;

    @CreationTimestamp
    private Timestamp registered;

    private Timestamp bannedDate;

    @OneToOne(mappedBy = "settings")
    private UserProfileEntity userProfileEntity;
}
