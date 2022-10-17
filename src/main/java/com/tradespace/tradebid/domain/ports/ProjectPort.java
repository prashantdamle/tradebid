package com.tradespace.tradebid.domain.ports;

import com.tradespace.tradebid.domain.model.Project;
import com.tradespace.tradebid.domain.model.ProjectBids;
import com.tradespace.tradebid.domain.model.ProjectDetails;

import java.util.List;
import java.util.UUID;

public interface ProjectPort {
    Project createNewProject(UUID userId, Project project);
    List<Project> getProjectsByUserId(UUID userId);
    ProjectBids getProjectByUserIdAndProjectId(UUID userId, UUID projectId);
    List<ProjectDetails> getActiveCustomerProjects(UUID tradesPersonId);
}
