package com.tradespace.tradebid.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class User {
    private final UUID userId;
    private final String userProfileName;
    private final UserType userType;
    private final String userName;
    private final String firstName;
    private final String lastName;
}
