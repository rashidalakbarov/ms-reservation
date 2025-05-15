package com.barber.reservation.constant;

import lombok.Getter;

@Getter
public enum MessageConstant {

    EMAIL_ALREADY_IN_USE("Email is already in use"),
    PHONE_NUMBER_ALREADY_IN_USE("Phone number is already in use"),
    USER_NOT_FOUND("User not found"),
    USER_NOT_FOUND_WITH_ID("User not found with ID: "),
    USER_NOT_FOUND_WITH_EMAIL_OR_PHONE("User not found with the provided email or phone number"),
    USER_CREATED_SUCCESSFULLY("User created successfully"),
    USER_UPDATED_SUCCESSFULLY("User updated successfully"),
    USER_DELETED_SUCCESSFULLY("User deleted successfully"),
    INVALID_PHONE_NUMBER_OR_PASSWORD("Invalid phone number or password"),
    RESERVATION_NOT_FOUND("Reservation not found with ID: "),
    BARBER_NOT_FOUND("Barber not found"),
    BARBER_ALREADY_BOOKED("Barber is already booked at the given time range.");

    private final String message;

    MessageConstant(String message) {
        this.message = message;
    }
}