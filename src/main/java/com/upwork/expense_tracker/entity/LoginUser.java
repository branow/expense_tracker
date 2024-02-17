package com.upwork.expense_tracker.entity;

import com.upwork.expense_tracker.dto.UserLoginRequest;

/**
 * It's better to use {@link UserLoginRequest}
 * */
@Deprecated
public class LoginUser {

    String email;
    String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
