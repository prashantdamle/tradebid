package com.tradespace.tradebid.adapters.service;

import com.tradespace.tradebid.domain.model.Project;
import com.tradespace.tradebid.domain.ports.JobCreatorPort;
import com.tradespace.tradebid.domain.ports.JobPort;
import lombok.RequiredArgsConstructor;
import org.jobrunr.jobs.JobId;
import org.jobrunr.scheduling.JobScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobCreatorAdapter implements JobCreatorPort {

    private final JobScheduler jobScheduler;
    private final JobPort jobPort;

    private static final Logger logger = LoggerFactory.getLogger(JobCreatorAdapter.class);

    @Override
    public void scheduleJobCreation(Project project) {
        final JobId scheduledJobId = jobScheduler.schedule(project.getExpiryDateTime(), () -> jobPort.createNewJob(project.getProjectId()));
        logger.info(String.format("Job creation scheduled [ScheduledJobId: %s] to run at [%s] for project: %s", scheduledJobId, project.getExpiryDateTime(), project.getProjectId()));
    }
}