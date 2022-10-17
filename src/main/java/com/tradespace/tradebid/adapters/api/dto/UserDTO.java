package com.tradespace.tradebid.adapters.api.dto;

import com.tradespace.tradebid.domain.model.User;
import com.tradespace.tradebid.domain.model.UserType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor()
public class UserDTO {
    private final UUID userId;
    private final String userProfileName;
    private final UserType userType;
    private final String userName;
    private final String firstName;
    private final String lastName;

    public static UserDTO fromUser(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(
                user.getUserId(),
                user.getUserProfileName(),
                user.getUserType(),
                user.getUserName(),
                user.getFirstName(),
                user.getLastName()
        );
    }
}
