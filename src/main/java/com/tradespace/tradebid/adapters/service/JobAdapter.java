package com.tradespace.tradebid.adapters.service;

import com.tradespace.tradebid.adapters.db.*;
import com.tradespace.tradebid.domain.exceptions.JobNotFoundException;
import com.tradespace.tradebid.domain.exceptions.ProjectNotFoundException;
import com.tradespace.tradebid.domain.exceptions.UserNotFoundException;
import com.tradespace.tradebid.domain.model.*;
import com.tradespace.tradebid.domain.ports.JobPort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class JobAdapter implements JobPort {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final BidRepository bidRepository;
    private final JobRepository jobRepository;

    private static final Logger logger = LoggerFactory.getLogger(JobAdapter.class);

    @Override
    @Transactional
    public void createNewJob(UUID projectId) {
        ProjectEntity projectEntity = projectRepository.findByProjectId(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));
        List<BidEntity> bids = projectEntity.getBids();

        if (bids == null || bids.isEmpty()) {
            logger.warn(format("Project with projectId [%s] has received no bids.", projectId));
            // Send a notification to the customer that his project has not received any bid
            return;
        }

        List<BidEntity> winningBids = getWinningBids(projectEntity);

        JobEntity jobEntity = new JobEntity(
                UUID.randomUUID(),
                projectEntity,
                winningBids
        );
        JobEntity newJobEntity = jobRepository.save(jobEntity);

        List<BidEntity> updatedWinningBids = winningBids.stream().map(winningBid -> {
            winningBid.setJob(newJobEntity);
            return winningBid;
        }).collect(toList());

        bidRepository.saveAll(updatedWinningBids);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Job> getJobs(UUID userId) {
        return jobRepository.findAllByProject_Poster_UserId(userId).map(jobEntities ->
                jobEntities.stream().map(JobEntity::toJob).collect(toList())
        ).orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public JobDetails getJobByUserIdAndJobId(UUID userId, UUID jobId) {
        JobDetails jobDetails = jobRepository.findByJobId(jobId).map(JobEntity::toJobDetails).orElseThrow(() -> new JobNotFoundException(jobId));

        if (!jobDetails.getProject().getPoster().getUserId().equals(userId)) {
            // This shouldn't happen! Log a warning and create and alert.
            logger.warn(format("Forbidden access by userId [%s] to jobId [%s]", userId, jobId));
            throw new JobNotFoundException(jobId);
        }
        return jobDetails;
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobWon> getJobsWon(UUID tradesPersonId) {
        userRepository.findByUserId(tradesPersonId).orElseThrow(() -> new UserNotFoundException(tradesPersonId));

        List<JobDetails> jobDetailsList = bidRepository.findAllByBidder_UserId_AndJobIsNotNull(tradesPersonId)
                .map(bidEntities ->
                        bidEntities.stream()
                                .map(BidEntity::getJob)
                                .map(JobEntity::toJobDetails)
                                .collect(toList())
                ).orElseThrow(() -> new UserNotFoundException(tradesPersonId));

        return jobDetailsList.stream().map(jobDetails -> {
            Bid tradesPersonBid = jobDetails.getWinningBids().stream().filter(bid -> bid.getBidder().getUserId().equals(tradesPersonId)).findFirst().get();
            return new JobWon(jobDetails.getJobId(), jobDetails.getProject(), tradesPersonBid);
        }).collect(toList());
    }

    private List<BidEntity> getWinningBids(ProjectEntity projectEntity) {
        float lowestBidPrice = Float.MAX_VALUE;
        List<BidEntity> winningBids = new ArrayList<>();

        for (BidEntity bid : projectEntity.getBids()) {
            float totalBidPrice = getTotalBidPrice(bid, projectEntity.getExpectedHours());

            if (totalBidPrice == lowestBidPrice) {
                winningBids.add(bid);
            } else if (totalBidPrice < lowestBidPrice) {
                winningBids.clear();
                winningBids.add(bid);
                lowestBidPrice = totalBidPrice;
            }
        }
        return winningBids;
    }

    private float getTotalBidPrice(BidEntity bid, long expectedHours) {
        if (bid.getBidType() == BidType.HOURLY) {
            return expectedHours * bid.getPrice();
        }
        return bid.getPrice();
    }
}