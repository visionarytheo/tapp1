package com.tapp.tserver.model;

import java.time.Instant;

public record User(
    Long userId,
    String firstName,
    String lastName,
    String email,
    String image,
    String role,
    Instant createdAt,
    Instant lastLoginAt
) {}


    

