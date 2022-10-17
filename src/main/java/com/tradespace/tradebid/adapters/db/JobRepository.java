package com.tradespace.tradebid.adapters.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, Long> {
    Optional<JobEntity> findByJobId(UUID jobId);
    Optional<List<JobEntity>> findAllByProject_Poster_UserId(UUID userId);
}