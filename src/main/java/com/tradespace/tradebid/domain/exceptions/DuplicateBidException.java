package com.tradespace.tradebid.domain.exceptions;

import java.util.UUID;

import static java.lang.String.format;

public class DuplicateBidException extends RuntimeException {
    public DuplicateBidException(UUID userId, UUID projectId) {
        super(format("UserId %s has already bid for ProjectId %s", userId, projectId));
    }
}
