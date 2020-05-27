package com.auction.game.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "T_USER_PROFILE")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class UserProfileEntity {
    @Id
    @Column(nullable = false, updatable = false, unique = true)
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String description;

    @Column
    private byte[] image;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private ProfileSettingsEntity settings;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credential_id", referencedColumnName = "id")
    private CredentialEntity credential;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "userProfile")
    private AuctioneerEntity auctioneerEntity;
}
