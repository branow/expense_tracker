package com.upwork.expense_tracker.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upwork.expense_tracker.constant.Messages;
import com.upwork.expense_tracker.entity.LoginUser;
import com.upwork.expense_tracker.entity.UpdateProfile;
import com.upwork.expense_tracker.entity.User;
import com.upwork.expense_tracker.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    InputsChecking inputsChecking;

    @Autowired
    TokenUtility tokenUtility;

    public Object createUser(User user) {

        Map<String, String> messages = inputsChecking.checkCreateUser(user);
        if (!messages.isEmpty()) {
            return messages;
        }

        userRepository.save(user);
        return Arrays.asList(Messages.SUCCESS);
    }

    public Object loginUser(LoginUser loginUser) {

        Map<String, String> messages = inputsChecking.checkLoginUser(loginUser);
        if (!messages.isEmpty()) {
            return messages;
        }
        return Arrays.asList(tokenUtility.generateToken(loginUser.getEmail()));
    }

    public Object updateProfile(String token, UpdateProfile updateProfile) {

        if (token == null) {
            return Arrays.asList(Messages.EMPTY_TOKEN);
        }
        if (!tokenUtility.validateToken(token)) {
            return Arrays.asList(Messages.INVALID_TOKEN);
        }
        String userName = tokenUtility.extractUsername(token);

        Map<String, String> messages = inputsChecking.checkUpdateProfile(updateProfile);
        if (!messages.isEmpty()) {
            return messages;
        }

        User user = userRepository.findByEmail(userName);
        user.setProfile(updateProfile.getProfile());

        userRepository.save(user);
        return Arrays.asList(Messages.SUCCESS);
    }

    public List<String> signout(String token) {

        tokenUtility.revokeToken(token);
        return Arrays.asList(Messages.SIGNOUT);
    }

    public Map<String, String> getUser(String token) {

        HashMap<String, String> map = new LinkedHashMap<>();
        if (token == null) {
            map.put("error", Messages.EMPTY_TOKEN);
            return map;
        }
        if (!tokenUtility.validateToken(token)) {
            map.put("error", Messages.INVALID_TOKEN);
            return map;
        }
        String userName = tokenUtility.extractUsername(token);
        User user = userRepository.findByEmail(userName);

        map.put("user_name", user.getEmail());
        map.put("profile", user.getProfile());
        return map;
    }

    public List<String> refreshToken(String token) {
        return Arrays.asList(tokenUtility.refreshAccessToken(token));
    }
}
