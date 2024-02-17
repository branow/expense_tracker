package com.upwork.expense_tracker.entity;

import com.upwork.expense_tracker.dto.ProfileUpdatingRequest;

/**
 * It's better to use {@link ProfileUpdatingRequest}
 * */
@Deprecated
public class UpdateProfile {

    private String profile;

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
