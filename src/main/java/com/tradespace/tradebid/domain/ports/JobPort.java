package com.tradespace.tradebid.domain.ports;

import com.tradespace.tradebid.domain.model.Job;
import com.tradespace.tradebid.domain.model.JobDetails;
import com.tradespace.tradebid.domain.model.JobWon;

import java.util.List;
import java.util.UUID;

public interface JobPort {
    void createNewJob(UUID projectId);
    List<Job> getJobs(UUID userId);
    JobDetails getJobByUserIdAndJobId(UUID userId, UUID jobId);
    List<JobWon> getJobsWon(UUID tradesPersonId);
}