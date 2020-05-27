package com.auction.game.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "T_MATERIAL")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Material {
    @Id
    @Column(nullable = false, updatable = false, unique = true)
    private String id = UUID.randomUUID().toString();

    @Column
    private String materials;
}
