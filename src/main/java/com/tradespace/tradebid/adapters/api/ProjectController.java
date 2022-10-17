package com.tradespace.tradebid.adapters.api;

import com.tradespace.tradebid.adapters.api.dto.CreateProjectDTO;
import com.tradespace.tradebid.adapters.api.dto.ProjectBidsDTO;
import com.tradespace.tradebid.adapters.api.dto.ProjectDTO;
import com.tradespace.tradebid.adapters.api.dto.ProjectDetailsDTO;
import com.tradespace.tradebid.domain.model.Project;
import com.tradespace.tradebid.domain.model.ProjectBids;
import com.tradespace.tradebid.domain.ports.ProjectPort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProjectController {

    private final ProjectPort projectPort;

    @RequestMapping(value = "/api/v1/user/{userId}/project", method = RequestMethod.POST)
    public ProjectDTO createProject(@NotNull @PathVariable("userId") UUID userId, @RequestBody @Valid CreateProjectDTO createProjectDTO) {
        Project project = projectPort.createNewProject(userId, createProjectDTO.toProject());
        return ProjectDTO.fromProject(project);
    }

    @RequestMapping(value = "/api/v1/user/{userId}/projects", method = RequestMethod.GET)
    public List<ProjectDTO> getProjects(@NotNull @PathVariable("userId") UUID userId) {
        return projectPort.getProjectsByUserId(userId).stream().map(project -> ProjectDTO.fromProject(project)).collect(toList());
    }

    @RequestMapping(value = "/api/v1/user/{userId}/project/{projectId}", method = RequestMethod.GET)
    public ProjectBidsDTO getProject(@NotNull @PathVariable("userId") UUID userId, @NotNull @PathVariable("projectId") UUID projectId) {
        ProjectBids projectBids = projectPort.getProjectByUserIdAndProjectId(userId, projectId);
        return ProjectBidsDTO.fromProjectBids(projectBids);
    }

    @RequestMapping(value = "/api/v1/user/{tradesPersonId}/customer-projects", method = RequestMethod.GET)
    public List<ProjectDetailsDTO> getCustomerProjects(@NotNull @PathVariable("tradesPersonId") UUID tradesPersonId) {
        return projectPort.getActiveCustomerProjects(tradesPersonId).stream().map(projectDetails -> ProjectDetailsDTO.fromProjectDetails(projectDetails)).collect(toList());
    }

}
