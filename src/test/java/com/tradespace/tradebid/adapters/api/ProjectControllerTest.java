package com.tradespace.tradebid.adapters.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradespace.tradebid.adapters.api.dto.CreateProjectDTO;
import com.tradespace.tradebid.adapters.api.dto.ProjectDTO;
import com.tradespace.tradebid.config.ControllerTestConfig;
import com.tradespace.tradebid.domain.model.Project;
import com.tradespace.tradebid.domain.model.ProjectType;
import com.tradespace.tradebid.domain.ports.ProjectPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {ControllerTestConfig.class})
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ProjectPort projectPort;


    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Should create new project")
    public void shouldCreateNewProject() throws Exception {
        UUID userId = UUID.randomUUID();

        CreateProjectDTO createProjectDTO = new CreateProjectDTO(ProjectType.ELECTRICAL, "Install down lights", "Install down lights in family room", 2L, OffsetDateTime.now().plusDays(5));
        Project project = createProjectDTO.toProject();

        when(projectPort.createNewProject(eq(userId), any())).thenReturn(project);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(String.format("/api/v1/user/%s/project", userId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createProjectDTO))
                )
                .andExpect(status().isOk())
                .andReturn();

        ProjectDTO savedProject = mapper.readValue(result.getResponse().getContentAsString(), ProjectDTO.class);

        assertNotNull(savedProject.getProjectId());
        assertEquals(createProjectDTO.getProjectType(), savedProject.getProjectType());
        assertEquals(createProjectDTO.getName(), savedProject.getName());
        assertEquals(createProjectDTO.getDescription(), savedProject.getDescription());
        assertEquals(createProjectDTO.getExpectedHours(), savedProject.getExpectedHours());
//        TODO Fix date format
//        assertEquals(createProjectDTO.getExpiryDateTime(), savedProject.getExpiryDateTime());
    }

}