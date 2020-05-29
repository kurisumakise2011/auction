package com.auction.game.converter;

import com.auction.game.entity.ProfileSettingsEntity;
import com.auction.game.entity.UserProfileEntity;
import com.auction.game.model.Credential;
import com.auction.game.model.ProfileSettings;
import com.auction.game.model.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class UserProfileConverter {

    public UserProfile toUserProfile(UserProfileEntity entity) {
        if (entity == null) {
            return null;
        }

        UserProfile profile = new UserProfile();

        profile.setEmail(entity.getEmail());
        profile.setId(entity.getId());
        profile.setDescription(entity.getDescription());
        profile.setUsername(entity.getUsername());

        ProfileSettings settings = new ProfileSettings();
        ProfileSettingsEntity entitySettings = entity.getSettings();
        settings.setBanned(entitySettings.getBanned());
        settings.setBannedDate(entitySettings.getBannedDate());
        settings.setOnline(entitySettings.getOnline());
        settings.setRegistered(entitySettings.getRegistered());
        settings.setRole(entitySettings.getRole());
        profile.setSettings(settings);

        Credential credential = new Credential();
        credential.setAlgo(entity.getCredential().getAlgo());
        credential.setExpired(entity.getCredential().getExpired());
        credential.setPassword(entity.getCredential().getPassword());
        profile.setCredential(credential);

        return profile;
    }

    public UserProfileEntity toEntity(UserProfile userProfile) {
        if (userProfile == null || userProfile.getSettings() == null) {
            return null;
        }

        ProfileSettings settings = userProfile.getSettings();

        UserProfileEntity entity = new UserProfileEntity();
        ProfileSettingsEntity settingsEntity = new ProfileSettingsEntity();
        settingsEntity.setBanned(settings.getBanned());
        settingsEntity.setBannedDate(settings.getBannedDate());
        settingsEntity.setRegistered(settings.getRegistered());
        settingsEntity.setRole(settings.getRole());

        entity.setSettings(settingsEntity);
        entity.setEmail(userProfile.getEmail());
        entity.setUsername(userProfile.getUsername());
        entity.setDescription(userProfile.getDescription());
        if (userProfile.getImage() != null) {
            entity.setImage(userProfile.getImage());
        }

        return entity;
    }

}
