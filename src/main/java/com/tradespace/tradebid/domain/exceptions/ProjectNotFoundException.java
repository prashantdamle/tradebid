package com.tradespace.tradebid.domain.exceptions;

import java.util.UUID;

import static java.lang.String.format;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(UUID projectId) {
        super(format("ProjectId %s not found.", projectId));
    }
}
