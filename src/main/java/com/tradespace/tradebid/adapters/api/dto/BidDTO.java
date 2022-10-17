package com.tradespace.tradebid.adapters.api.dto;

import com.tradespace.tradebid.domain.model.Bid;
import com.tradespace.tradebid.domain.model.BidType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

import static com.tradespace.tradebid.adapters.api.dto.UserDTO.fromUser;

@Getter
@RequiredArgsConstructor
public class BidDTO {
    private final UUID bidId;
    private final BidType bidType;
    private final Float price;
    private final OffsetDateTime createdDateTime;
    private final UserDTO bidder;

    public static BidDTO fromBid(Bid bid) {
        if (bid == null) {
            return null;
        }
        return new BidDTO(
                bid.getBidId(),
                bid.getBidType(),
                bid.getPrice(),
                bid.getCreatedDateTime(),
                fromUser(bid.getBidder())
        );
    }
}
