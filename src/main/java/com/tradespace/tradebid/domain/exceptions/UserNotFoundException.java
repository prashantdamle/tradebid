package com.tradespace.tradebid.domain.exceptions;

import java.util.UUID;

import static java.lang.String.format;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID userId) {
        super(format("UserId %s not found.", userId));
    }

    public UserNotFoundException(String userName) {
        super(format("UserName %s not found.", userName));
    }
}
