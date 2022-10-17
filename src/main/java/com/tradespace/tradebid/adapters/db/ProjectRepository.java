package com.tradespace.tradebid.adapters.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    Optional<ProjectEntity> findByProjectId(UUID projectId);
    @Query("from Project p where p.poster.userId <> :tradesPersonId and p.expiryDateTime > :endDateTime " +
            "and p.projectId not in (select b.project.projectId from Bid b where b.bidder.userId = :tradesPersonId)")
    List<ProjectEntity> findActiveProjectsWherePosterIdNotEqualToTradesPersonId(UUID tradesPersonId, OffsetDateTime endDateTime);
}