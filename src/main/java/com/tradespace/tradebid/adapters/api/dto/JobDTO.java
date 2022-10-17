package com.tradespace.tradebid.adapters.api.dto;

import com.tradespace.tradebid.domain.model.Job;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class JobDTO {
    private final UUID jobId;
    private final ProjectDTO project;

    public static JobDTO fromJob(Job job) {
        if (job == null) {
            return null;
        }
        return new JobDTO(
                job.getJobId(),
                ProjectDTO.fromProject(job.getProject())
        );
    }
}
