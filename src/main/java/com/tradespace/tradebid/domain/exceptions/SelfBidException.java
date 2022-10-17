package com.tradespace.tradebid.domain.exceptions;

import java.util.UUID;

import static java.lang.String.format;

public class SelfBidException extends RuntimeException {
    public SelfBidException(UUID userId, UUID projectId) {
        super(format("Project posters cannot bid on their own projects. UserId %s, ProjectId %s", userId, projectId));
    }
}
