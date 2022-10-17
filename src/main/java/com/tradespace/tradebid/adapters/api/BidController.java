package com.tradespace.tradebid.adapters.api;

import com.tradespace.tradebid.adapters.api.dto.BidDTO;
import com.tradespace.tradebid.adapters.api.dto.BidProjectDTO;
import com.tradespace.tradebid.adapters.api.dto.CreateBidDTO;
import com.tradespace.tradebid.domain.model.Bid;
import com.tradespace.tradebid.domain.ports.BidPort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BidController {

    private final BidPort bidPort;

    @RequestMapping(value = "/api/v1/user/{userId}/customer-project/{projectId}/bid", method = RequestMethod.POST)
    public BidDTO bid(@NotNull @PathVariable("userId") UUID userId,
                      @NotNull @PathVariable("projectId") UUID projectId,
                      @RequestBody @Valid CreateBidDTO createBidDTO) {
        Bid bid = bidPort.bid(userId, projectId, createBidDTO.toBid());
        return BidDTO.fromBid(bid);
    }

    @RequestMapping(value = "/api/v1/user/{userId}/bid-projects", method = RequestMethod.GET)
    public List<BidProjectDTO> getBidProjects(@NotNull @PathVariable("userId") UUID userId) {
        List<Bid> bids = bidPort.getBidProjects(userId);
        return bids.stream().map(BidProjectDTO::fromBid).collect(toList());
    }

}
