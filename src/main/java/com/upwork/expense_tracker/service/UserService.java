package com.upwork.expense_tracker.service;

import java.util.Arrays;
import java.util.List;

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

    public List<String> createUser(User user) {

        List<String> messages = inputsChecking.checkCreateUser(user);
        if (!messages.isEmpty()) {
            return messages;
        }

        userRepository.save(user);
        return Arrays.asList(Messages.SUCCESS);
    }

    public List<String> loginUser(LoginUser loginUser) {

        List<String> messages = inputsChecking.checkLoginUser(loginUser);
        if (!messages.isEmpty()) {
            return messages;
        }
        return Arrays.asList(tokenUtility.generateToken(loginUser.getEmail()));
    }

    public List<String> updateProfile(String token, UpdateProfile updateProfile) {

        if (token == null) {
            return Arrays.asList(Messages.EMPTY_TOKEN);
        }
        if (!tokenUtility.validateToken(token)) {
            return Arrays.asList(Messages.INVALID_TOKEN);
        }
        String userName = tokenUtility.extractUsername(token);

        List<String> messages = inputsChecking.checkUpdateProfile(updateProfile);
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
}
