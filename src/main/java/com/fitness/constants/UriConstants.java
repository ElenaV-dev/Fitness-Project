package com.fitness.constants;

public final class UriConstants {

    public static final String REDIRECT = "redirect:";

    public static final String ADMIN = "/admin";
    public static final String TRAINER = "/trainer";
    public static final String ACCOUNT = "/account";
    public static final String USERS = "/users";
    public static final String WORKOUT_TYPES = "/workout-types";
    public static final String SUBSCRIPTION = "/subscription";
    public static final String TRAINING_RECORDS = "/training-records";

    public static final String REDIRECT_ADMIN_WORKOUT_TYPES = REDIRECT + ADMIN + WORKOUT_TYPES;
    public static final String REDIRECT_ADMIN_USERS = REDIRECT + ADMIN + USERS;

    public static final String REDIRECT_TRAINER_WORKOUT_TYPES = REDIRECT + TRAINER + WORKOUT_TYPES;

    public static final String REDIRECT_ACCOUNT_WORKOUT_TYPES = REDIRECT + ACCOUNT + WORKOUT_TYPES;
    public static final String REDIRECT_ACCOUNT_SUBSCRIPTION = REDIRECT + ACCOUNT + SUBSCRIPTION;
    public static final String REDIRECT_ACCOUNT_TRAINING_RECORDS = REDIRECT + ACCOUNT + TRAINING_RECORDS;

    private UriConstants() {
    }
}
