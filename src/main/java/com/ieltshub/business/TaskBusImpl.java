package com.ieltshub.business;

import com.ieltshub.entity.Task;
import com.ieltshub.enumeration.StatusType;
import com.ieltshub.enumeration.TaskStatus;
import com.ieltshub.exception.BusinessException;
import com.ieltshub.repository.TaskRepository;
import com.ieltshub.utility.DateUtility;
import com.ieltshub.viewmodel.cache.UserInfo;
import com.ieltshub.viewmodel.common.PaginationResult;
import com.ieltshub.viewmodel.task.TaskFilter;
import com.ieltshub.viewmodel.task.TaskModel;
import com.ieltshub.viewmodel.task.taskTime.TaskTimeConfigModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskBusImpl extends AbstractBusiness implements TaskBus {


    @Autowired
    TaskRepository taskRepo;

    @Override
    public PaginationResult<TaskModel> getListTask(TaskFilter filter, UserInfo userInfo) {
        if(!filter.getDay().equals("All")) {
            String day = filter.getMonth() + "-" + filter.getDay();
            filter.setDay(day);
        } else {
            filter.setDay(null);
        }
        PaginationResult<Task> taskPaginationResult = taskRepo.getListTask(filter, userInfo.getUserId());
        PaginationResult<TaskModel> result = new PaginationResult<>(filter.getPaging());
        result.setData(taskPaginationResult.getData().stream().map(item -> {
            TaskModel model = new TaskModel(item);
            return model;
        }).collect(Collectors.toList()));
        return result;
    }

    @Override
    public TaskModel getTaskDetail(Long taskId, UserInfo userInfo) {
        Task task = taskRepo.getTaskById(taskId, StatusType.DELETE.getValue());
        TaskModel model = new TaskModel(task);
        return model;
    }

    @Override
    @Transactional
    public Boolean updateTask(Long taskId, TaskFilter filter, UserInfo userInfo) {
        initialize();
        Task task = taskRepo.getTaskById(taskId, StatusType.DELETE.getValue());
        if(task != null) {
            if(filter.getTaskType() != null) {
                task.setTaskStatus(TaskStatus.parse(filter.getTaskType()));
            }
            task.setCompletedAt(sysTimestamp);
            task.setSetCompletedBy(userRepo.findById(userInfo.getUserId()).get());
            task.setUpdatedAt(sysTimestamp);
            task.setUpdatedBy(userRepo.findById(userInfo.getUserId()).get());
            taskRepo.save(task);
            return true;
        }
        return false;
    }

    @Override
    public List<TaskTimeConfigModel> getTaskTimeConfig(UserInfo userInfo, String platform) throws BusinessException {
        initialize();
        String toDay = DateUtility.formatDateWithTimeZone(sysdate, DateUtility.FORMAT_DATE_SQL, "Asia/Saigon");
        List<Task> taskList = taskRepo.getTaskByDay(userInfo.getUserId(), toDay, StatusType.DELETE.getValue());
        List<TaskTimeConfigModel> taskTimeConfigModels = new ArrayList<>();
        if(taskList.size() > 0) {
            for(Task task: taskList) {
                taskTimeConfigModels.add(new TaskTimeConfigModel(task));
            }
            return taskTimeConfigModels;
        }
        return null;
    }

}
