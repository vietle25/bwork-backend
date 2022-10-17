package com.evowork.repository;

import com.evowork.entity.Task;
import com.evowork.viewmodel.common.PaginationResult;
import com.evowork.viewmodel.task.TaskFilter;

public interface TaskRepositoryExtend {

    /**
     * Get list task user
     * @param filter
     * @param userId
     * @return
     */
    PaginationResult<Task> getListTask(TaskFilter filter, Long userId);
}
