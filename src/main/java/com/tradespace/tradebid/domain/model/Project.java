package com.tradespace.tradebid.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class Project {
    private final UUID projectId;
    private final ProjectType projectType;
    private final String name;
    private final String description;
    private final Long expectedHours;
    private final OffsetDateTime createdDateTime;
    private final OffsetDateTime expiryDateTime;
}
