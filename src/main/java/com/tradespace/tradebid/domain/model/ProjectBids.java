package com.tradespace.tradebid.domain.model;

import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class ProjectBids extends ProjectDetails {
    private final List<Bid> bids;

    public ProjectBids(UUID projectId, ProjectType projectType, String name, String description, Long expectedHours, OffsetDateTime createdDateTime, OffsetDateTime expiryDateTime, User poster, List<Bid> bids) {
        super(projectId, projectType, name, description, expectedHours, createdDateTime, expiryDateTime, poster);
        this.bids = bids;
    }
}
