package com.upwork.expense_tracker.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

@Deprecated
@Service
public class InputsChecking {

    @Autowired
    UserRepository userRepository;

    public List<String> checkCreateUser(User user) {

        List<String> response = new ArrayList<>();

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            response.add(Messages.EMPTY_EMAIL);
        } else if (userRepository.existsByEmail(user.getEmail())) {
            response.add(Messages.EMAIL_ALREADY_EXISTS);
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            response.add(Messages.EMPTY_PASSWORD);
        }
        if (user.getProfile() == null || user.getProfile().isEmpty()) {
            response.add(Messages.EMPTY_PROFILE);
        } else if (!isBase64Image(user.getProfile())) {
            response.add(Messages.INVALID_IMAGE);
        }

        return response;
    }

    public List<String> checkLoginUser(LoginUser loginUser) {

        List<String> response = new ArrayList<>();

        if (loginUser.getEmail() == null || loginUser.getEmail().isEmpty()) {
            response.add(Messages.EMPTY_EMAIL);
        } else if (!userRepository.existsByEmail(loginUser.getEmail())) {
            response.add(Messages.WRONG_EMAIL);
        }

        if (loginUser.getPassword() == null || loginUser.getPassword().isEmpty()) {
            response.add(Messages.EMPTY_PASSWORD);

        } else if (loginUser.getEmail() != null
                && !userRepository.existsByEmailAndPassword(loginUser.getEmail(), loginUser.getPassword())) {
            response.add(Messages.WRONG_PASSWORD);
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

    public List<String> checkCreateTransaction(Transaction transaction, String methodName) {

        List<String> response = new ArrayList<>();

        if (methodName.equals(Messages.UPDATE) && transaction.getId() == null) {
            response.add(Messages.EMPTY_TRANSACTION_ID);
        }

        if (transaction.getType() == null || transaction.getType().isEmpty()) {
            response.add(Messages.EMPTY_TYPE);
        } else if (!transaction.getType().equals(Messages.EARNED) && !transaction.getType().equals(Messages.SPENT)) {
            response.add(Messages.INVALID_TYPE);
        }
        if (transaction.getDescription() == null || transaction.getDescription().isEmpty()) {
            response.add(Messages.EMPTY_DESCRIPTION);
        }

        return response;
    }

    public List<String> checkUpdateTransaction(Transaction transaction) {

        List<String> response = new ArrayList<>();

        if (transaction.getId() == null) {
            response.add(Messages.EMPTY_TRANSACTION_ID);
        }
        if (transaction.getType() == null || transaction.getType().isEmpty()) {
            response.add(Messages.EMPTY_TYPE);
        } else if (!transaction.getType().equals(Messages.EARNED) && !transaction.getType().equals(Messages.SPENT)) {
            response.add(Messages.INVALID_TYPE);
        }
        if (transaction.getDescription() == null || transaction.getDescription().isEmpty()) {
            response.add(Messages.EMPTY_DESCRIPTION);
        }

        return response;
    }

    public List<String> checkUpdateProfile(UpdateProfile updateProfile) {

        List<String> response = new ArrayList<>();

        if (updateProfile.getProfile() == null || updateProfile.getProfile().isEmpty()) {
            response.add(Messages.EMPTY_PROFILE);
        } else if (!isBase64Image(updateProfile.getProfile())) {
            response.add(Messages.INVALID_IMAGE);
        }
        return response;
    }

}
