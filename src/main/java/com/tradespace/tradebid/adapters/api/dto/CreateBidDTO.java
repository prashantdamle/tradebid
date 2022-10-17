package com.tradespace.tradebid.adapters.api.dto;

import com.tradespace.tradebid.domain.model.Bid;
import com.tradespace.tradebid.domain.model.BidType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class CreateBidDTO {

    @NotNull
    private final BidType bidType;

    @NotNull
    private final Float price;

    public Bid toBid() {
        return new Bid(
                UUID.randomUUID(),
                bidType,
                price,
                OffsetDateTime.now(),
                null,
                null
        );
    }
}
