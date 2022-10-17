package com.tradespace.tradebid.domain.model;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class UserDetails extends User {
    private final List<Project> projects;

    public UserDetails(UUID userId, String userProfileName, UserType userType, String userName, String firstName, String lastName, List<Project> projects) {
        super(userId, userProfileName, userType, userName, firstName, lastName);
        this.projects = projects;
    }
}
