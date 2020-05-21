package com.auction.game.service;

import com.auction.game.model.UserProfile;
import com.auction.game.web.LoginRequest;

import java.util.List;

public interface UserService {

    String login(LoginRequest loginRequest);

    UserProfile registration(UserProfile profile);

    UserProfile getUserById(String userId);

    UserProfile getUserByUsernameOrEmail(String identification);

    List<UserProfile> users();

    String findIdByUsername(String username);

    void banUser(boolean banned, String settingsId);
}
