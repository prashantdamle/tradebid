package com.tradespace.tradebid.domain.exceptions;

import java.util.UUID;

import static java.lang.String.format;

public class JobNotFoundException extends RuntimeException {
    public JobNotFoundException(UUID jobId) {
        super(format("JobId %s not found.", jobId));
    }
}
