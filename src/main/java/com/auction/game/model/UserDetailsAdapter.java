package com.auction.game.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserDetailsAdapter implements UserDetails {
    private final UserProfile userProfile;

    public UserDetailsAdapter(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRole role = Optional.ofNullable(userProfile)
                .map(UserProfile::getSettings)
                .map(ProfileSettings::getRole)
                .orElse(null);
        if (role == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(UserRole.values())
                .filter(userRole -> userRole.ordinal() <= role.ordinal())
                .map(userRole -> new SimpleGrantedAuthority(userRole.name()))
                .collect(Collectors.toList());
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
        return !userProfile.getSettings().getBanned();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !userProfile.getSettings().getBanned();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !userProfile.getCredential().getExpired();
    }

    @Override
    public boolean isEnabled() {
        return !userProfile.getSettings().getBanned();
    }

    public String id() {
        return userProfile.getId();
    }
}
