package com.tradespace.tradebid.adapters.service;

import com.tradespace.tradebid.adapters.db.*;
import com.tradespace.tradebid.domain.exceptions.*;
import com.tradespace.tradebid.domain.model.Bid;
import com.tradespace.tradebid.domain.ports.BidPort;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static java.time.OffsetDateTime.now;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class BidAdapter implements BidPort {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final BidRepository bidRepository;

    @Override
    @Transactional
    public Bid bid(UUID userId, UUID projectId, Bid bid) {
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        ProjectEntity projectEntity = projectRepository.findByProjectId(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));

        if (userEntity.getId() == projectEntity.getPoster().getId()) {
            throw new SelfBidException(userId, projectId);
        }
        if (now().compareTo(projectEntity.getExpiryDateTime()) >= 0) {
            throw new ProjectExpiredException(projectId);
        }

        BidEntity bidEntity = BidEntity.fromBid(bid);
        bidEntity.setBidder(userEntity);
        bidEntity.setProject(projectEntity);

        try {
            return bidRepository.save(bidEntity).toBid();
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateBidException(userId, projectId);
        }
    }

    @Override
    public List<Bid> getBidProjects(UUID userId) {
        userRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return bidRepository.findAllByBidder_UserId(userId).orElse(emptyList()).stream().map(BidEntity::toBid).collect(toList());
    }

}
