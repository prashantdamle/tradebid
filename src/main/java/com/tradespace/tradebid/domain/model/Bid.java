package com.tradespace.tradebid.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class Bid {
    private final UUID bidId;
    private final BidType bidType;
    private final Float price;
    private final OffsetDateTime createdDateTime;
    private final Project project;
    private final User bidder;
}
