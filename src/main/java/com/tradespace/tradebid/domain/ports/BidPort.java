package com.tradespace.tradebid.domain.ports;

import com.tradespace.tradebid.domain.model.Bid;

import java.util.List;
import java.util.UUID;

public interface BidPort {
    Bid bid(UUID userId, UUID projectId, Bid bid);
    List<Bid> getBidProjects(UUID userId);
}
