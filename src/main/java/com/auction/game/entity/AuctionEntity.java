package com.auction.game.entity;

import com.auction.game.model.AuctionStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "T_AUCTION")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class AuctionEntity {
    @Id
    @Column(nullable = false, updatable = false, unique = true)
    private String id = UUID.randomUUID().toString();

    @Column(name = "started", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp started;

    @Column(name = "updated", nullable = false)
    @UpdateTimestamp
    private Timestamp updated;

    @Column(nullable = false)
    private Timestamp end;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AuctionStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_id")
    private AuctioneerEntity winner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private AuctioneerEntity author;

    @ManyToMany
    @JoinTable(name = "participants",
            joinColumns = @JoinColumn(name = "participant_id"),
            inverseJoinColumns = @JoinColumn(name = "auction_id"))
    private List<AuctioneerEntity> participants;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "auctionEntity")
    private List<BidEntity> bids;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private ItemEntity auctionItemEntity;
}
