package com.tradespace.tradebid.config;

import com.tradespace.tradebid.domain.ports.BidPort;
import com.tradespace.tradebid.domain.ports.JobPort;
import com.tradespace.tradebid.domain.ports.ProjectPort;
import com.tradespace.tradebid.domain.ports.UserPort;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class ControllerTestConfig {

    @MockBean
    private UserPort userPort;

    @MockBean
    private ProjectPort projectPort;

    @MockBean
    private BidPort bidPort;

    @MockBean
    private JobPort jobPort;
}
