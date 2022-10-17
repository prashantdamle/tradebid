package com.tradespace.tradebid.domain.model;

import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
public class ProjectDetails extends Project {
    private final User poster;

    public ProjectDetails(UUID projectId, ProjectType projectType, String name, String description, Long expectedHours, OffsetDateTime createdDateTime, OffsetDateTime expiryDateTime, User poster) {
        super(projectId, projectType, name, description, expectedHours, createdDateTime, expiryDateTime);
        this.poster = poster;
    }
}
