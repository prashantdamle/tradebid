package com.tradespace.tradebid.domain.exceptions;

import java.util.UUID;

import static java.lang.String.format;

public class ProjectExpiredException extends RuntimeException {
    public ProjectExpiredException(UUID projectId) {
        super(format("ProjectId %s has already expired.", projectId));
    }
}
