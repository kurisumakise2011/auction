package com.auction.game.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "T_CREDENTIAL")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class CredentialEntity {
    @Id
    @Column(nullable = false, updatable = false, unique = true)
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false)
    private String algo;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean expired;

    @OneToOne(mappedBy = "credential")
    private UserProfileEntity profile;
}

