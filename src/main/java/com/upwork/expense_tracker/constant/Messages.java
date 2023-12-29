package com.upwork.expense_tracker.constant;

public class Messages {

    private Messages() {

    }

    // Success
    public static final String SUCCESS = "success";

    // Deleted
    public static final String DELETED = "deleted";

    // Signout
    public static final String SIGNOUT = "signout";

    // Method action
    public static final String CREATE = "create";
    public static final String UPDATE = "update";

    // type
    public static final String EARNED = "earned";
    public static final String SPENT = "spent";

    // Empty inputs
    public static final String EMPTY_EMAIL = "empty email";
    public static final String EMPTY_PASSWORD = "empty password";
    public static final String EMPTY_PROFILE = "empty profile";
    public static final String EMPTY_TYPE = "empty type";
    public static final String EMPTY_DESCRIPTION = "empty description";
    public static final String EMPTY_TOKEN = "empty token";
    public static final String EMPTY_TRANSACTION_ID = "empty transaction id";

    // Invalid Inputs
    public static final String INVALID_IMAGE = "invalid image";
    public static final String EMAIL_ALREADY_EXISTS = "email already exists";
    public static final String WRONG_EMAIL = "wrong email";
    public static final String WRONG_PASSWORD = "wrong password";
    public static final String INVALID_TYPE = "type should be earned or spent";
    public static final String INVALID_TOKEN = "invalid token";
    public static final String INVALID_TRANSACTION = "invalid transaction";

}
