package com.evowork.repository;

import com.evowork.entity.AppConfig;
import com.evowork.viewmodel.user.AppConfigFilter;

import java.util.List;

public interface AppConfigRepositoryExtend {

    /**
     * Get mobile config
     * @param filter
     * @return
     */
    List<AppConfig> getMobileConfig(AppConfigFilter filter);
}
