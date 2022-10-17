package com.tradespace.tradebid.adapters.api;

import com.tradespace.tradebid.adapters.api.dto.JobDTO;
import com.tradespace.tradebid.adapters.api.dto.JobDetailsDTO;
import com.tradespace.tradebid.adapters.api.dto.JobWonDTO;
import com.tradespace.tradebid.domain.exceptions.JobNotFoundException;
import com.tradespace.tradebid.domain.model.JobDetails;
import com.tradespace.tradebid.domain.ports.JobPort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

import static com.tradespace.tradebid.adapters.api.dto.JobDetailsDTO.fromJobDetails;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class JobController {

    private final JobPort jobPort;

    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    @RequestMapping(value = "/api/v1/user/{userId}/jobs", method = RequestMethod.GET)
    public List<JobDTO> getJobs(@NotNull @PathVariable("userId") UUID userId) {
        return jobPort.getJobs(userId).stream().map(JobDTO::fromJob).collect(toList());
    }

    @RequestMapping(value = "/api/v1/user/{userId}/job/{jobId}", method = RequestMethod.GET)
    public JobDetailsDTO getJob(@NotNull @PathVariable("userId") UUID userId, @NotNull @PathVariable("jobId") UUID jobId) {
        JobDetails jobDetails = jobPort.getJobByUserIdAndJobId(userId, jobId);

        if (!jobDetails.getProject().getPoster().getUserId().equals(userId)) {
            // This shouldn't happen! Log a warning and create and alert.
            logger.warn(format("Forbidden access by userId [%s] to jobId [%s]", userId, jobId));
            throw new JobNotFoundException(jobId);
        }
        return fromJobDetails(jobDetails);
    }

    @RequestMapping(value = "/api/v1/user/{tradesPersonId}/jobs-won", method = RequestMethod.GET)
    public List<JobWonDTO> getJobsWon(@NotNull @PathVariable("tradesPersonId") UUID tradesPersonId) {
        return jobPort.getJobsWon(tradesPersonId).stream().map(JobWonDTO::fromJobWon).collect(toList());
    }

}
