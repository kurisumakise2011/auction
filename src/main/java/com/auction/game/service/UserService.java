package com.auction.game.service;

import com.auction.game.model.UserProfile;
import com.auction.game.web.LoginRequest;

public interface UserService {

    String login(LoginRequest loginRequest);

    UserProfile registration(UserProfile profile);

    UserProfile getUserById(String userId);

    UserProfile getUserByUsernameOrEmail(String identification);

    String findIdByUsername(String username);

}
