package com.auction.game.model;

import lombok.Data;

import javax.management.relation.Role;
import java.sql.Timestamp;

@Data
public class ProfileSettings {
    private String id;
    private UserRole role;
    private Boolean banned;
    private Boolean online;
    private Timestamp registered;
    private Timestamp bannedDate;
}
