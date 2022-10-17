package com.evowork.repository;

import com.evowork.entity.WorkingTimeConfig;

public interface WorkingTimeConfigRepositoryExtend {

    /**
     * Get working time config
     *
     * @param userId
     * @return
     */
    WorkingTimeConfig getWorkingTimeConfig(Long userId, String toDay, Long companyId, Boolean isFuture);

}
