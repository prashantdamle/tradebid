package com.tradespace.tradebid.adapters.api.dto;

import com.tradespace.tradebid.domain.model.ProjectBids;
import com.tradespace.tradebid.domain.model.ProjectType;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Getter
public class ProjectBidsDTO extends ProjectDTO {
    private final List<BidDTO> bids;

    public ProjectBidsDTO(UUID projectId, ProjectType projectType, String name, String description, Long expectedHours, OffsetDateTime createdDateTime, OffsetDateTime expiryDateTime, List<BidDTO> bids) {
        super(projectId, projectType, name, description, expectedHours, createdDateTime, expiryDateTime);
        this.bids = bids;
    }

    public static ProjectBidsDTO fromProjectBids(ProjectBids projectBids) {
        if (projectBids == null) {
            return null;
        }
        return new ProjectBidsDTO(
                projectBids.getProjectId(),
                projectBids.getProjectType(),
                projectBids.getName(),
                projectBids.getDescription(),
                projectBids.getExpectedHours(),
                projectBids.getCreatedDateTime(),
                projectBids.getExpiryDateTime(),
                projectBids.getBids() != null ? projectBids.getBids().stream().map(BidDTO::fromBid).collect(toList()) : emptyList()
        );
    }
}
