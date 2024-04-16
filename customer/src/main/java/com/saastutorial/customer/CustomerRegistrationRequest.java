package com.saastutorial.customer;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email) {
}
