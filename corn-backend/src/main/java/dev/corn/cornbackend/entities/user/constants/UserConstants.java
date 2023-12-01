package dev.corn.cornbackend.entities.user.constants;

public final class UserConstants {

    public static final String USERS_TABLE_NAME = "users";

    public static final String USER_NAME_FIELD_NAME = "name";
    public static final String USER_NAME_BLANK_MSG = "Name cannot be null and has to contain at least one non-whitespace character";
    public static final String USER_NAME_WRONG_SIZE_MSG = "Name must consist of max 40 characters";
    public static final int USER_NAME_MAX_SIZE = 40;

    public static final String USER_SURNAME_FIELD_NAME = "surname";
    public static final String USER_SURNAME_BLANK_MSG = "Surname cannot be null and has to contain at least one non-whitespace character";
    public static final String USER_SURNAME_WRONG_SIZE_MSG = "Surname must consist of max 50 characters";
    public static final int USER_SURNAME_MAX_SIZE = 50;

    public static final String USER_USERNAME_FIELD_NAME = "username";
    public static final String USER_USERNAME_BLANK_MSG = "Username cannot be null and has to contain at least one non-whitespace character";
    public static final String USER_USERNAME_WRONG_SIZE_MSG = "Username must consist of max 50 characters";
    public static final int USER_USERNAME_MAX_SIZE = 50;

    private UserConstants() {

    }
}
