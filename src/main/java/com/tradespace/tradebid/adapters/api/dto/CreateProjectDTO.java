package com.tradespace.tradebid.adapters.api.dto;

import com.tradespace.tradebid.domain.model.Project;
import com.tradespace.tradebid.domain.model.ProjectType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class CreateProjectDTO {

    @NotNull
    private final ProjectType projectType;
    @NotNull
    private final String name;
    @NotNull
    private final String description;
    @NotNull
    private final Long expectedHours;
    @NotNull
    private final OffsetDateTime expiryDateTime;

    public Project toProject() {
        return new Project(
                UUID.randomUUID(),
                projectType,
                name,
                description,
                expectedHours,
                OffsetDateTime.now(),
                expiryDateTime
        );
    }
}
