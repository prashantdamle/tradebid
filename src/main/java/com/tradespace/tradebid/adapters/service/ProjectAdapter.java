package com.tradespace.tradebid.adapters.service;

import com.tradespace.tradebid.adapters.db.ProjectEntity;
import com.tradespace.tradebid.adapters.db.ProjectRepository;
import com.tradespace.tradebid.adapters.db.UserEntity;
import com.tradespace.tradebid.adapters.db.UserRepository;
import com.tradespace.tradebid.domain.exceptions.ProjectNotFoundException;
import com.tradespace.tradebid.domain.exceptions.UserNotFoundException;
import com.tradespace.tradebid.domain.model.Project;
import com.tradespace.tradebid.domain.model.ProjectBids;
import com.tradespace.tradebid.domain.model.ProjectDetails;
import com.tradespace.tradebid.domain.model.User;
import com.tradespace.tradebid.domain.ports.JobCreatorPort;
import com.tradespace.tradebid.domain.ports.ProjectPort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static java.lang.String.format;
import static java.time.OffsetDateTime.now;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ProjectAdapter implements ProjectPort {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final JobCreatorPort jobCreatorPort;

    private static final Logger logger = LoggerFactory.getLogger(ProjectAdapter.class);

    @Override
    @Transactional
    public Project createNewProject(UUID userId, Project project) {
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        ProjectEntity projectEntity = ProjectEntity.fromProject(project);
        projectEntity.setPoster(userEntity);

        ProjectEntity saveProjectEntity = projectRepository.save(projectEntity);

        // Schedule job creation at project expiry time
        jobCreatorPort.scheduleJobCreation(project);

        return saveProjectEntity.toProjectBids();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> getProjectsByUserId(UUID userId) {
        return userRepository.findByUserId(userId).map(UserEntity::toUserDetails)
                .orElseThrow(() -> new UserNotFoundException(userId)).getProjects();
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectBids getProjectByUserIdAndProjectId(UUID userId, UUID projectId) {
        ProjectBids projectBids = projectRepository.findByProjectId(projectId).map(ProjectEntity::toProjectBids)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));

        if (!projectBids.getPoster().getUserId().equals(userId)) {
            // This shouldn't happen! Log a warning and create and alert.
            logger.warn(format("Forbidden access by userId [%s] to projectId [%s]", userId, projectId));
            throw new ProjectNotFoundException(projectId);
        }
        return projectBids;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectDetails> getActiveCustomerProjects(UUID tradesPersonId) {
        User tradesPerson = userRepository.findByUserId(tradesPersonId).map(UserEntity::toUser)
                .orElseThrow(() -> new UserNotFoundException(tradesPersonId));

        return projectRepository.findActiveProjectsWherePosterIdNotEqualToTradesPersonId(tradesPerson.getUserId(), now())
                .stream().map(ProjectEntity::toProjectDetails).collect(toList());
    }
}
