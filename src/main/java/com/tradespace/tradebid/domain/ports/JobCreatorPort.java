package com.tradespace.tradebid.domain.ports;

import com.tradespace.tradebid.domain.model.Project;

public interface JobCreatorPort {
    void scheduleJobCreation(Project project);
}
