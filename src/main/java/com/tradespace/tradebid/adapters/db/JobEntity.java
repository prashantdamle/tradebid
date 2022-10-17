package com.tradespace.tradebid.adapters.db;

import com.tradespace.tradebid.domain.model.Job;
import com.tradespace.tradebid.domain.model.JobDetails;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;


@Data
@NoArgsConstructor
@Entity(name = "Job")
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "jobId")
        }
)
public class JobEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Type(type = "uuid-char")
    @NotNull
    private UUID jobId;

    @OneToOne(optional = false)
    @JoinColumn(unique = true, updatable = false)
    private ProjectEntity project;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<BidEntity> bids;

    public JobEntity(UUID jobId, ProjectEntity project, List<BidEntity> bids) {
        this.jobId = jobId;
        this.project = project;
        this.bids = bids;
    }

    public Job toJob() {
        return new Job(
                jobId,
                project.toProject()
        );
    }

    public JobDetails toJobDetails() {
        return new JobDetails(
                jobId,
                project.toProjectDetails(),
                bids.stream().map(BidEntity::toBid).collect(toList())
        );
    }

}
