package com.upwork.expense_tracker.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;
import java.awt.image.BufferedImage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.upwork.expense_tracker.constant.Messages;
import com.upwork.expense_tracker.entity.LoginUser;
import com.upwork.expense_tracker.entity.Transaction;
import com.upwork.expense_tracker.entity.UpdateProfile;
import com.upwork.expense_tracker.entity.User;
import com.upwork.expense_tracker.repository.UserRepository;

@Service
public class InputsChecking {

    @Autowired
    UserRepository userRepository;

    public Map<String, String> checkCreateUser(User user) {

        Map<String, String> response = new LinkedHashMap<>();

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            response.put("error_code", Messages.EMPTY_EMAIL);
            response.put("error_description", Messages.EMPTY_EMAIL_MESSAGE);

        } else if (userRepository.existsByEmail(user.getEmail())) {
            response.put("error_code", Messages.EMAIL_ALREADY_EXISTS);
            response.put("error_description", Messages.EMAIL_ALREADY_EXISTS_MESSAGE);

        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            response.put("error_code", Messages.EMPTY_PASSWORD);
            response.put("error_description", Messages.EMPTY_PASSWORD_MESSAGE);
        }
        if (user.getProfile() == null || user.getProfile().isEmpty()) {
            response.put("error_code", Messages.EMPTY_PROFILE);
            response.put("error_description", Messages.EMPTY_PROFILE_MESSAGE);
        } else if (!isBase64Image(user.getProfile())) {
            response.put("error_code", Messages.INVALID_IMAGE);
            response.put("error_description", Messages.INVALID_IMAGE_MESSAGE);
        }

        return response;
    }

    public Map<String, String> checkLoginUser(LoginUser loginUser) {

        Map<String, String> response = new LinkedHashMap<>();

        if (loginUser.getEmail() == null || loginUser.getEmail().isEmpty()) {
            response.put("error_code", Messages.EMPTY_EMAIL);
            response.put("error_description", Messages.EMPTY_EMAIL_MESSAGE);
        } else if (!userRepository.existsByEmail(loginUser.getEmail())) {
            response.put("error_code", Messages.WRONG_EMAIL);
            response.put("error_description", Messages.WRONG_EMAIL_MESSAGE);
        }

        if (loginUser.getPassword() == null || loginUser.getPassword().isEmpty()) {
            response.put("error_code", Messages.EMPTY_PASSWORD);
            response.put("error_description", Messages.EMPTY_PASSWORD_MESSAGE);

        } else if (loginUser.getEmail() != null
                && !userRepository.existsByEmailAndPassword(loginUser.getEmail(), loginUser.getPassword())) {

            response.put("error_code", Messages.WRONG_PASSWORD);
            response.put("error_description", Messages.WRONG_PASSWORD_MESSAGE);
        }

        return response;
    }

    private boolean isBase64Image(String base64String) {
        try {
            byte[] imageBytes = Base64.decodeBase64(base64String);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);

            BufferedImage image = ImageIO.read(bis);
            return image != null;

        } catch (IOException e) {
            return false;
        }
    }

    public Map<String, String> checkCreateTransaction(Transaction transaction, String methodName) {

        Map<String, String> response = new LinkedHashMap<>();

        if (methodName.equals(Messages.UPDATE) && transaction.getId() == null) {
            response.put("error_code", Messages.EMPTY_TRANSACTION_ID);
            response.put("error_description", Messages.EMPTY_TRANSACTION_ID_MESSAGE);
        }

        if (transaction.getType() == null || transaction.getType().isEmpty()) {
            response.put("error_code", Messages.EMPTY_TYPE);
            response.put("error_description", Messages.EMPTY_TYPE_MESSAGE);
        } else if (!transaction.getType().equals(Messages.EARNED) && !transaction.getType().equals(Messages.SPENT)) {
            response.put("error_code", Messages.INVALID_TYPE);
            response.put("error_description", Messages.INVALID_TYPE_MESSAGE);
        }
        if (transaction.getDescription() == null || transaction.getDescription().isEmpty()) {
            response.put("error_code", Messages.EMPTY_DESCRIPTION);
            response.put("error_description", Messages.EMPTY_DESCRIPTION_MESSAGE);
        }

        return response;
    }

    public Map<String, String> checkUpdateTransaction(Transaction transaction) {

        Map<String, String> response = new LinkedHashMap<>();

        if (transaction.getId() == null) {
            response.put("error_code", Messages.EMPTY_TRANSACTION_ID);
            response.put("error_description", Messages.EMPTY_TRANSACTION_ID_MESSAGE);
        }
        if (transaction.getType() == null || transaction.getType().isEmpty()) {
            response.put("error_code", Messages.EMPTY_TYPE);
            response.put("error_description", Messages.EMPTY_TYPE_MESSAGE);
        } else if (!transaction.getType().equals(Messages.EARNED) && !transaction.getType().equals(Messages.SPENT)) {
            response.put("error_code", Messages.INVALID_TYPE);
            response.put("error_description", Messages.INVALID_TYPE_MESSAGE);
        }
        if (transaction.getDescription() == null || transaction.getDescription().isEmpty()) {
            response.put("error_code", Messages.EMPTY_DESCRIPTION);
            response.put("error_description", Messages.EMPTY_DESCRIPTION_MESSAGE);
        }

        return response;
    }

    public Map<String, String> checkUpdateProfile(UpdateProfile updateProfile) {

        Map<String, String> response = new LinkedHashMap<>();

        if (updateProfile.getProfile() == null || updateProfile.getProfile().isEmpty()) {
            response.put("error_code", Messages.EMPTY_PROFILE);
            response.put("error_description", Messages.EMPTY_PROFILE_MESSAGE);
        } else if (!isBase64Image(updateProfile.getProfile())) {
            response.put("error_code", Messages.INVALID_IMAGE);
            response.put("error_description", Messages.INVALID_IMAGE_MESSAGE);
        }
        return response;
    }
}
