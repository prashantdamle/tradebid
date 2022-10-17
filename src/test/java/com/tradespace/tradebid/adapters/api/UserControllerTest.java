package com.tradespace.tradebid.adapters.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradespace.tradebid.adapters.api.dto.ErrorDTO;
import com.tradespace.tradebid.adapters.api.dto.UserDTO;
import com.tradespace.tradebid.config.ControllerTestConfig;
import com.tradespace.tradebid.domain.exceptions.UserNotFoundException;
import com.tradespace.tradebid.domain.model.User;
import com.tradespace.tradebid.domain.model.UserType;
import com.tradespace.tradebid.domain.ports.UserPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {ControllerTestConfig.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserPort userPort;


    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Should get user by userName")
    public void shouldGetUserByUserName() throws Exception {
        String userName = "testUserName";
        User user = new User(UUID.randomUUID(), "testUserProfileName", UserType.CUSTOMER, userName, "Test", "User");

        when(userPort.getUserByUserName(eq(userName))).thenReturn(user);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(String.format("/api/v1/user/%s", userName))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        UserDTO userDTO = mapper.readValue(result.getResponse().getContentAsString(), UserDTO.class);

        assertEquals(user.getUserId(), userDTO.getUserId());
        assertEquals(user.getUserProfileName(), userDTO.getUserProfileName());
        assertEquals(user.getUserType(), userDTO.getUserType());
        assertEquals(user.getUserName(), userDTO.getUserName());
        assertEquals(user.getFirstName(), userDTO.getFirstName());
        assertEquals(user.getLastName(), userDTO.getLastName());
    }

    @Test
    @DisplayName("Should get 404 not found when no user exists by the given userName")
    public void shouldGetNotFoundError() throws Exception {
        String userName = "testUserName";

        when(userPort.getUserByUserName(eq(userName))).thenThrow(new UserNotFoundException(userName));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(String.format("/api/v1/user/%s", userName))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andReturn();

        ErrorDTO errorDTO = mapper.readValue(result.getResponse().getContentAsString(), ErrorDTO.class);

        assertEquals(UserNotFoundException.class.getSimpleName(), errorDTO.getType());
        assertEquals(valueOf(HttpStatus.NOT_FOUND.value()), errorDTO.getStatus());
        assertEquals(format("UserName %s not found.", userName), errorDTO.getError());
    }
}