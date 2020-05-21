package com.auction.game.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.List;


@Entity
@Table(name = "T_AUCTIONEER")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class AuctioneerEntity {
    @Id
    @Column(nullable = false, updatable = false, unique = true)
    private String id;

    @PrimaryKeyJoinColumn
    @OneToOne(fetch = FetchType.EAGER)
    private UserProfileEntity userProfile;

    @OneToMany(fetch = FetchType.LAZY)
    private List<BidEntity> bids;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "winner")
    private List<AuctionEntity> winner;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "author")
    private List<AuctionEntity> author;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "participants")
    private List<AuctionEntity> takeParticipant;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "holder")
    private List<ItemEntity> items;
}
