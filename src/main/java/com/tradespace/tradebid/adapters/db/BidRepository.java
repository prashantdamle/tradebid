package com.tradespace.tradebid.adapters.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BidRepository extends JpaRepository<BidEntity, Long> {
    Optional<List<BidEntity>> findAllByBidder_UserId(UUID userId);
    Optional<List<BidEntity>> findAllByBidder_UserId_AndJobIsNotNull(UUID userId);
}