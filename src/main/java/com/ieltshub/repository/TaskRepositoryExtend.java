package com.ieltshub.repository;

import com.ieltshub.entity.Task;
import com.ieltshub.viewmodel.common.PaginationResult;
import com.ieltshub.viewmodel.task.TaskFilter;

public interface TaskRepositoryExtend {

    /**
     * Get list task user
     * @param filter
     * @param userId
     * @return
     */
    PaginationResult<Task> getListTask(TaskFilter filter, Long userId);
}
