package com.tradespace.tradebid.adapters.api.dto;

import com.tradespace.tradebid.domain.model.JobWon;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class JobWonDTO {
    private final UUID jobId;
    private final ProjectDetailsDTO project;
    private final BidDTO bid;

    public static JobWonDTO fromJobWon(JobWon jobWon) {
        if (jobWon == null) {
            return null;
        }
        return new JobWonDTO(
                jobWon.getJobId(),
                ProjectDetailsDTO.fromProjectDetails(jobWon.getProject()),
                BidDTO.fromBid(jobWon.getBid())
        );
    }
}
