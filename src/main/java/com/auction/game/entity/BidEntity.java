package com.auction.game.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "T_BID")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class BidEntity {
    @Id
    @Column(nullable = false, updatable = false, unique = true)
    private String id = UUID.randomUUID().toString();

    @Column(name = "order_num", nullable = false, updatable = false)
    private Integer order;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp created;

    @Column(nullable = false)
    @Digits(integer=20, fraction=10)
    private BigDecimal price;

    @Column(nullable = false)
    private Boolean won;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id")
    private AuctionEntity auctionEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auctioneer_id")
    private AuctioneerEntity auctioneer;
}
