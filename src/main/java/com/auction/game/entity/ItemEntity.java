package com.auction.game.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "T_ITEM")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class ItemEntity {
    @Id
    @Column(nullable = false, updatable = false, unique = true)
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false, length = 1000)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    @Digits(integer=20, fraction=10)
    private Double price;

    @CreationTimestamp
    @Column(name = "published", nullable = false)
    private Timestamp published;

    @Column(nullable = false)
    private Boolean hidden;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "itemEntity")
    private List<ItemMediaEntity> medias;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "auctionItemEntity")
    private List<AuctionEntity> auctionItemEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "auctioneer_id")
    private AuctioneerEntity holder;

    @Column
    private String genre;

    @Column
    private String material;

    @Column
    private String category;
}
