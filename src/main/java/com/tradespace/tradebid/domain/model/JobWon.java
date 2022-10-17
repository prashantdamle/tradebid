package com.tradespace.tradebid.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class JobWon {
    private final UUID jobId;
    private final ProjectDetails project;
    private final Bid bid;
}
