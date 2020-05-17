package com.auction.game.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsAdapter implements UserDetails {
    private final UserProfile userProfile;

    public UserDetailsAdapter(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return userProfile.getCredential().getPassword();
    }

    @Override
    public String getUsername() {
        return userProfile.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !userProfile.getSettings().isBanned();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !userProfile.getSettings().isBanned();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !userProfile.getCredential().getExpired();
    }

    @Override
    public boolean isEnabled() {
        return !userProfile.getSettings().isBanned();
    }

    public String id() {
        return userProfile.getId();
    }
}
