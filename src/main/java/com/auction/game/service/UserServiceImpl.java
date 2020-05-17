package com.auction.game.service;

import com.auction.game.converter.UserProfileConverter;
import com.auction.game.entity.CredentialEntity;
import com.auction.game.entity.UserProfileEntity;
import com.auction.game.exception.UnknownUserException;
import com.auction.game.model.UserDetailsAdapter;
import com.auction.game.model.UserProfile;
import com.auction.game.repository.CredentialRepository;
import com.auction.game.repository.UserProfileRepository;
import com.auction.game.web.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Override
    public String login(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public UserProfile registration(UserProfile profile) {
        UserProfileEntity entity = userProfileConverter.toEntity(profile);
        CredentialEntity credentialEntity = new CredentialEntity();
        credentialEntity.setPassword(entity.getCredential().getPassword());
        credentialEntity.setAlgo(entity.getCredential().getAlgo());
        credentialEntity.setExpired(false);
        entity.setCredential(credentialEntity);


        String hash = DigestUtils.md5DigestAsHex(credentialEntity.getPassword().getBytes());
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
        return new UserDetailsAdapter(getUserByUsernameOrEmail(s));
    }
}
