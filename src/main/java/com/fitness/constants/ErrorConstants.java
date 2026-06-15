package com.fitness.constants;

public final class ErrorConstants {

    public static final String EMAIL_EXISTS = "User with this email already exists";
    public static final String WORKOUT_TYPE_EXISTS = "Workout type already exists";
    public static final String SUBSCRIPTION_EXISTS = "Active subscription already exists";
    public static final String WORKOUT_TYPE_BOOKED = "You are already registered for this workout";
    public static final String SUBSCRIPTION_REQUIRED = "Active subscription required";

    public static final String USER_NOT_FOUND_BY_ID = "User not found with id: ";
    public static final String USER_NOT_FOUND_BY_EMAIL = "User not found with email: ";
    public static final String WORKOUT_TYPE_NOT_FOUND_BY_ID = "Workout type not found with id: ";
    public static final String SUBSCRIPTION_NOT_FOUND_BY_USER_ID = "Subscription not found with user id: ";
    public static final String TRAINING_RECORD_NOT_FOUND_BY_ID = "Training record not found with id: ";

    private  ErrorConstants() {}
}
