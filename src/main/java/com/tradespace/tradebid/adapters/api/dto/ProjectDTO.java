package com.tradespace.tradebid.adapters.api.dto;

import com.tradespace.tradebid.domain.model.Project;
import com.tradespace.tradebid.domain.model.ProjectType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class ProjectDTO {
    private final UUID projectId;
    private final ProjectType projectType;
    private final String name;
    private final String description;
    private final Long expectedHours;
    private final OffsetDateTime createdDateTime;
    private final OffsetDateTime expiryDateTime;

    public static ProjectDTO fromProject(Project project) {
        if (project == null) {
            return null;
        }
        return new ProjectDTO(
                project.getProjectId(),
                project.getProjectType(),
                project.getName(),
                project.getDescription(),
                project.getExpectedHours(),
                project.getCreatedDateTime(),
                project.getExpiryDateTime()
        );
    }

}
