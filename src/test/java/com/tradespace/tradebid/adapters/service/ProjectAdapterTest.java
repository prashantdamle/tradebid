package com.tradespace.tradebid.adapters.service;

import com.tradespace.tradebid.adapters.db.ProjectEntity;
import com.tradespace.tradebid.adapters.db.ProjectRepository;
import com.tradespace.tradebid.adapters.db.UserEntity;
import com.tradespace.tradebid.adapters.db.UserRepository;
import com.tradespace.tradebid.domain.model.Project;
import com.tradespace.tradebid.domain.model.ProjectType;
import com.tradespace.tradebid.domain.model.UserType;
import com.tradespace.tradebid.domain.ports.JobCreatorPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectAdapterTest {

    @Captor
    ArgumentCaptor<Project> projectArgumentCaptor;
    @Captor
    ArgumentCaptor<ProjectEntity> projectEntityArgumentCaptor;

    @Mock
    private UserRepository userRepository;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private JobCreatorPort jobCreatorPort;

    @InjectMocks
    private ProjectAdapter projectAdapter;


    @Test
    @DisplayName("createNewProject should create new project")
    public void shouldCreateNewProject() {
        UUID userId = UUID.randomUUID();
        UserEntity userEntity = new UserEntity(userId, "testUserProfileName", UserType.CUSTOMER, "testUserName", "Test", "User", null);

        Project newProject = new Project(UUID.randomUUID(), ProjectType.ELECTRICAL, "Install down lights", "Install down lights in family room", 2L, OffsetDateTime.now(), OffsetDateTime.now().plusDays(5));
        ProjectEntity projectEntity = ProjectEntity.fromProject(newProject);
        projectEntity.setPoster(userEntity);

        when(userRepository.findByUserId(eq(userId))).thenReturn(Optional.of(userEntity));
        when(projectRepository.save(any())).thenReturn(projectEntity);

        Project savedProject = projectAdapter.createNewProject(userId, newProject);
        assertProjects(newProject, savedProject);

        verify(projectRepository).save(projectEntityArgumentCaptor.capture());
        assertProjectAndProjectEntity(newProject, projectEntityArgumentCaptor.getValue());

        verify(jobCreatorPort).scheduleJobCreation(projectArgumentCaptor.capture());
        assertProjects(newProject, projectArgumentCaptor.getValue());
    }

    private void assertProjects(Project project, Project otherProject) {
        assertEquals(project.getProjectId(), otherProject.getProjectId());
        assertEquals(project.getProjectType(), otherProject.getProjectType());
        assertEquals(project.getName(), otherProject.getName());
        assertEquals(project.getDescription(), otherProject.getDescription());
        assertEquals(project.getExpectedHours(), otherProject.getExpectedHours());
        assertEquals(project.getCreatedDateTime(), otherProject.getCreatedDateTime());
        assertEquals(project.getExpiryDateTime(), otherProject.getExpiryDateTime());
    }

    private void assertProjectAndProjectEntity(Project project, ProjectEntity projectEntity) {
        assertEquals(project.getProjectId(), projectEntity.getProjectId());
        assertEquals(project.getProjectType(), projectEntity.getProjectType());
        assertEquals(project.getName(), projectEntity.getName());
        assertEquals(project.getDescription(), projectEntity.getDescription());
        assertEquals(project.getExpectedHours(), projectEntity.getExpectedHours());
        assertEquals(project.getCreatedDateTime(), projectEntity.getCreatedDateTime());
        assertEquals(project.getExpiryDateTime(), projectEntity.getExpiryDateTime());
    }
}