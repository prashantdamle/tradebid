package com.tradespace.tradebid.adapters.db;

import com.tradespace.tradebid.domain.model.Bid;
import com.tradespace.tradebid.domain.model.BidType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

import static com.tradespace.tradebid.adapters.db.ProjectEntity.fromProject;
import static com.tradespace.tradebid.adapters.db.UserEntity.fromUser;

@Data
@NoArgsConstructor
@Entity(name = "Bid")
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "bidId"),
        @UniqueConstraint(columnNames = {"project_id", "bidder_id"})
})
public class BidEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Type(type = "uuid-char")
    @NotNull
    private UUID bidId;

    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private BidType bidType;

    @NotNull
    private Float price;

    @NotNull
    private OffsetDateTime createdDateTime;

    @ManyToOne(optional = false)
    private ProjectEntity project;

    @ManyToOne(optional = false)
    private UserEntity bidder;

    @ManyToOne
    private JobEntity job;

    public BidEntity(UUID bidId, BidType bidType, Float price, OffsetDateTime createdDateTime, ProjectEntity project, UserEntity bidder) {
        this.bidId = bidId;
        this.bidType = bidType;
        this.price = price;
        this.createdDateTime = createdDateTime;
        this.project = project;
        this.bidder = bidder;
    }

    public Bid toBid() {
        return new Bid(
                bidId,
                bidType,
                price,
                createdDateTime,
                project.toProject(),
                bidder.toUser()
        );
    }

    public static BidEntity fromBid(Bid bid) {
        if (bid == null) {
            return null;
        }
        return new BidEntity(
                bid.getBidId(),
                bid.getBidType(),
                bid.getPrice(),
                bid.getCreatedDateTime(),
                fromProject(bid.getProject()),
                fromUser(bid.getBidder()));
    }
}
