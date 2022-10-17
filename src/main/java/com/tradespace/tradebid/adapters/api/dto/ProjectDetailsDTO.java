package com.tradespace.tradebid.adapters.api.dto;

import com.tradespace.tradebid.domain.model.ProjectDetails;
import com.tradespace.tradebid.domain.model.ProjectType;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
public class ProjectDetailsDTO extends ProjectDTO {
    private final UserDTO poster;

    public ProjectDetailsDTO(UUID projectId, ProjectType projectType, String name, String description, Long expectedHours, OffsetDateTime createdDateTime, OffsetDateTime expiryDateTime, UserDTO poster) {
        super(projectId, projectType, name, description, expectedHours, createdDateTime, expiryDateTime);
        this.poster = poster;
    }

    public static ProjectDetailsDTO fromProjectDetails(ProjectDetails projectDetails) {
        if (projectDetails == null) {
            return null;
        }
        return new ProjectDetailsDTO(
                projectDetails.getProjectId(),
                projectDetails.getProjectType(),
                projectDetails.getName(),
                projectDetails.getDescription(),
                projectDetails.getExpectedHours(),
                projectDetails.getCreatedDateTime(),
                projectDetails.getExpiryDateTime(),
                UserDTO.fromUser(projectDetails.getPoster())
        );
    }

}
