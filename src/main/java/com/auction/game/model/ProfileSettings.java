package com.auction.game.model;

import lombok.Data;

import javax.management.relation.Role;
import java.sql.Timestamp;

@Data
public class ProfileSettings {
    private UserRole role;
    private boolean banned;
    private boolean online;
    private Timestamp registered;
    private Timestamp bannedDate;
}
