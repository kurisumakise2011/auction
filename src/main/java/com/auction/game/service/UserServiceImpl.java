package com.auction.game.service;

import com.auction.game.converter.UserProfileConverter;
import com.auction.game.entity.CredentialEntity;
import com.auction.game.entity.UserProfileEntity;
import com.auction.game.exception.UnknownUserException;
import com.auction.game.model.ProfileSettings;
import com.auction.game.model.UserDetailsAdapter;
import com.auction.game.model.UserProfile;
import com.auction.game.model.UserRole;
import com.auction.game.repository.CredentialRepository;
import com.auction.game.repository.UserProfileRepository;
import com.auction.game.web.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.transaction.Transactional;
import java.util.Optional;

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
    private PasswordEncoder encoder;

    @Override
    public String login(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public UserProfile registration(UserProfile profile) {
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

        return userProfileConverter.toUserProfile(userProfileRepository.save(entity));
    }

    @Override
    public UserProfile getUserById(String userId) {
        Optional<UserProfileEntity> byId = userProfileRepository.findById(userId);
        if (byId.isEmpty()) {
            throw new UnknownUserException("No such user with id " + userId);
        }
        return userProfileConverter.toUserProfile(byId.get());
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
    public String findIdByUsername(String username) {
        return userProfileRepository.findIdByUsername(username);
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
