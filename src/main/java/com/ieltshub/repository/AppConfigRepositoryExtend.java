package com.ieltshub.repository;

import com.ieltshub.entity.AppConfig;
import com.ieltshub.viewmodel.user.AppConfigFilter;

import java.util.List;

public interface AppConfigRepositoryExtend {

    /**
     * Get mobile config
     * @param filter
     * @return
     */
    List<AppConfig> getMobileConfig(AppConfigFilter filter);
}
