package com.tradespace.tradebid.adapters.api.dto;

import com.tradespace.tradebid.domain.model.JobDetails;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Getter
@RequiredArgsConstructor
public class JobDetailsDTO {
    private final UUID jobId;
    private final ProjectDetailsDTO project;
    private final List<BidDTO> winningBids;

    public static JobDetailsDTO fromJobDetails(JobDetails jobDetails) {
        if (jobDetails == null) {
            return null;
        }
        return new JobDetailsDTO(
                jobDetails.getJobId(),
                ProjectDetailsDTO.fromProjectDetails(jobDetails.getProject()),
                jobDetails.getWinningBids().stream().map(BidDTO::fromBid).collect(toList())
        );
    }
}
