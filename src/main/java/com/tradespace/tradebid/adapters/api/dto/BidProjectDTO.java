package com.tradespace.tradebid.adapters.api.dto;

import com.tradespace.tradebid.domain.model.Bid;
import com.tradespace.tradebid.domain.model.BidType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class BidProjectDTO {
    private final UUID bidId;
    private final BidType bidType;
    private final Float price;
    private final OffsetDateTime createdDateTime;
    private final ProjectDTO project;

    public static BidProjectDTO fromBid(Bid bid) {
        if (bid == null) {
            return null;
        }
        return new BidProjectDTO(
                bid.getBidId(),
                bid.getBidType(),
                bid.getPrice(),
                bid.getCreatedDateTime(),
                ProjectDTO.fromProject(bid.getProject())
        );
    }
}
