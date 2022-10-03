package com.ieltshub.business;

import com.ieltshub.exception.BusinessException;
import com.ieltshub.viewmodel.cache.UserInfo;
import com.ieltshub.viewmodel.common.PaginationResult;
import com.ieltshub.viewmodel.task.TaskFilter;
import com.ieltshub.viewmodel.task.TaskModel;
import com.ieltshub.viewmodel.task.taskTime.TaskTimeConfigModel;

import java.util.List;

public interface TaskBus {

    /**
     * Get list task user
     * @param taskFilter
     * @param userInfo
     * @return
     */
    PaginationResult<TaskModel> getListTask(TaskFilter taskFilter, UserInfo userInfo) throws BusinessException;

    /**
     * Get task detail
     * @param taskId
     * @param userInfo
     * @return
     * @throws BusinessException
     */
    TaskModel getTaskDetail(Long taskId, UserInfo userInfo) throws BusinessException;

    /**
     * Update status task
     * @param taskId
     * @param filter
     * @param userInfo
     * @return
     * @throws BusinessException
     */
    Boolean updateTask(Long taskId, TaskFilter filter, UserInfo userInfo) throws BusinessException;

    /**
     * Get task time config
     *
     * @throws BusinessException
     */
    List<TaskTimeConfigModel> getTaskTimeConfig(UserInfo userInfo, String platform) throws BusinessException;

}
