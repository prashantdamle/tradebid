package com.tradespace.tradebid.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class JobDetails {
    private final UUID jobId;
    private final ProjectDetails project;
    private final List<Bid> winningBids;
}
