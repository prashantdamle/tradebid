package com.tradespace.tradebid.adapters.service;

import com.tradespace.tradebid.adapters.db.UserEntity;
import com.tradespace.tradebid.adapters.db.UserRepository;
import com.tradespace.tradebid.domain.exceptions.UserNotFoundException;
import com.tradespace.tradebid.domain.model.User;
import com.tradespace.tradebid.domain.model.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAdapterTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserAdapter userAdapter;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("getUserByUserName should return user")
    public void getUserByUserNameShouldReturnUser() {
        String username = "testUserName";

        UserEntity userEntity = new UserEntity(UUID.randomUUID(), "testUserProfileName", UserType.CUSTOMER, username, "Test", "User", null);
        when(userRepository.findByUserName(eq(username))).thenReturn(Optional.of(userEntity));

        User user = userAdapter.getUserByUserName(username);

        assertEquals(userEntity.getUserId(), user.getUserId());
        assertEquals(userEntity.getUserProfileName(), user.getUserProfileName());
        assertEquals(userEntity.getUserType(), user.getUserType());
        assertEquals(userEntity.getUserName(), user.getUserName());
        assertEquals(userEntity.getFirstName(), user.getFirstName());
        assertEquals(userEntity.getLastName(), user.getLastName());
    }

    @Test
    @DisplayName("getUserByUserName should throw UserNotFoundException")
    public void getUserByUserNameShouldThrowUserNotFoundException() {
        String username = "testUserName";

        when(userRepository.findByUserName(eq(username))).thenThrow(new UserNotFoundException(username));

        assertThrows(UserNotFoundException.class, () -> userAdapter.getUserByUserName(username), String.format("UserName %s not found.", username));
    }
}