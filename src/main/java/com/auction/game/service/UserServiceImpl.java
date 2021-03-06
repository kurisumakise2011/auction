package com.auction.game.service;

import com.auction.game.converter.UserProfileConverter;
import com.auction.game.entity.AuctioneerEntity;
import com.auction.game.entity.CredentialEntity;
import com.auction.game.entity.UserProfileEntity;
import com.auction.game.exception.UnknownUserException;
import com.auction.game.model.ProfileSettings;
import com.auction.game.model.UserDetailsAdapter;
import com.auction.game.model.UserProfile;
import com.auction.game.model.UserRole;
import com.auction.game.repository.CredentialRepository;
import com.auction.game.repository.ProfileSettingsRepository;
import com.auction.game.repository.UserProfileRepository;
import com.auction.game.web.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private UserProfileConverter userProfileConverter;

    @Autowired
    private ProfileSettingsRepository profileSettingsRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public String login(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public UserProfile registration(UserProfile profile) {
        if (userProfileRepository.existsByEmail(profile.getEmail())) {
            throw new UserAlreadyRegisteredException("Such user already registered with that email");
        }
        if (userProfileRepository.existsByUsername(profile.getUsername())) {
            throw new UserAlreadyRegisteredException("Such user already registered with that username");
        }


        ProfileSettings settings = new ProfileSettings();
        settings.setBanned(false);
        settings.setRole(UserRole.REGISTERED);
        profile.setSettings(settings);

        UserProfileEntity entity = userProfileConverter.toEntity(profile);
        CredentialEntity credentialEntity = new CredentialEntity();
        credentialEntity.setPassword(profile.getCredential().getPassword());
        credentialEntity.setAlgo(profile.getCredential().getAlgo());
        credentialEntity.setExpired(false);
        entity.setCredential(credentialEntity);
        String hash = encoder.encode(credentialEntity.getPassword());
        credentialEntity.setPassword(hash);

        AuctioneerEntity auctioneerEntity = new AuctioneerEntity();
        auctioneerEntity.setId(entity.getId());

        entity.setAuctioneerEntity(auctioneerEntity);

        UserProfileEntity save = userProfileRepository.save(entity);

        return userProfileConverter.toUserProfile(save);
    }

    @Override
    public UserProfile getUserById(String userId) {
        UserProfileEntity byId = userProfileRepository.findById(userId).orElse(null);
        if (byId == null) {
            throw new UnknownUserException("No such user with id " + userId);
        }
        UserProfile profile = userProfileConverter.toUserProfile(byId);
        profile.setHasAvatar(byId.getImage() != null);
        profile.setCredential(null);
        return profile;
    }

    @Override
    public UserProfile getUserByUsernameOrEmail(String identification) {
        if (identification.contains("@")) {
            return userProfileConverter.toUserProfile(userProfileRepository.findByEmail(identification));
        } else {
            return userProfileConverter.toUserProfile(userProfileRepository.findByUsername(identification));
        }
    }

    @Override
    public List<UserProfile> users() {
        return userProfileRepository.findAll()
                .stream()
                .map(user -> userProfileConverter.toUserProfile(user))
                .collect(Collectors.toList());
    }

    @Override
    public String findIdByUsername(String username) {
        return userProfileRepository.findIdByUsername(username);
    }

    @Override
    public void banUser(boolean banned, String userId) {
        UserProfileEntity entity = userProfileRepository.findById(userId).orElse(null);
        if (entity == null) {
            return;
        }
        entity.getSettings().setBanned(banned);
        userProfileRepository.save(entity);
    }

    @Override
    public void updateDescription(String description, String id) {
        userProfileRepository.updateDescription(description, id);
    }

    @Override
    public void updateImage(String id, byte[] image) {
        userProfileRepository.updateImage(image, id);
    }

    @Override
    public byte[] getImage(String userId) {
        return userProfileRepository.getImageById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserProfile userByUsernameOrEmail = getUserByUsernameOrEmail(s);
        if (userByUsernameOrEmail == null) {
            throw new UsernameNotFoundException(s);
        }
        return new UserDetailsAdapter(userByUsernameOrEmail);
    }
}
