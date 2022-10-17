package com.tradespace.tradebid.adapters.db;

import com.tradespace.tradebid.domain.model.Project;
import com.tradespace.tradebid.domain.model.ProjectBids;
import com.tradespace.tradebid.domain.model.ProjectDetails;
import com.tradespace.tradebid.domain.model.ProjectType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;


@Data
@NoArgsConstructor
@Entity(name = "Project")
@Table(
        indexes = {
                @Index(columnList = "poster_id"),
                @Index(columnList = "expiryDateTime")
        },
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "projectId")
        }
)
public class ProjectEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Type(type = "uuid-char")
    @NotNull
    private UUID projectId;

    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private ProjectType projectType;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Long expectedHours;

    @NotNull
    private OffsetDateTime createdDateTime;

    @NotNull
    private OffsetDateTime expiryDateTime;

    @ManyToOne(optional = false)
    private UserEntity poster;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BidEntity> bids;

    public ProjectEntity(UUID projectId, ProjectType projectType, String name, String description, Long expectedHours, OffsetDateTime createdDateTime, OffsetDateTime expiryDateTime, UserEntity poster, List<BidEntity> bids) {
        this.projectId = projectId;
        this.projectType = projectType;
        this.name = name;
        this.description = description;
        this.expectedHours = expectedHours;
        this.createdDateTime = createdDateTime;
        this.expiryDateTime = expiryDateTime;
        this.poster = poster;
        this.bids = bids;
    }

    public Project toProject() {
        return new Project(
                projectId,
                projectType,
                name,
                description,
                expectedHours,
                createdDateTime,
                expiryDateTime
        );
    }

    public ProjectDetails toProjectDetails() {
        return new ProjectDetails(
                projectId,
                projectType,
                name,
                description,
                expectedHours,
                createdDateTime,
                expiryDateTime,
                poster.toUser()
        );
    }

    public ProjectBids toProjectBids() {
        return new ProjectBids(
                projectId,
                projectType,
                name,
                description,
                expectedHours,
                createdDateTime,
                expiryDateTime,
                poster.toUser(),
                bids != null ? bids.stream().map(BidEntity::toBid).collect(toList()) : emptyList()
        );
    }

    public static ProjectEntity fromProject(Project project) {
        if (project == null) {
            return null;
        }
        return new ProjectEntity(
                project.getProjectId(),
                project.getProjectType(),
                project.getName(),
                project.getDescription(),
                project.getExpectedHours(),
                project.getCreatedDateTime(),
                project.getExpiryDateTime(),
                null,
                null
        );
    }

    public static ProjectEntity fromProjectDetails(ProjectDetails projectDetails) {
        if (projectDetails == null) {
            return null;
        }
        return new ProjectEntity(
                projectDetails.getProjectId(),
                projectDetails.getProjectType(),
                projectDetails.getName(),
                projectDetails.getDescription(),
                projectDetails.getExpectedHours(),
                projectDetails.getCreatedDateTime(),
                projectDetails.getExpiryDateTime(),
                UserEntity.fromUser(projectDetails.getPoster()),
                emptyList()
        );
    }

}
