package com.evowork.repository;

import com.evowork.entity.Task;
import com.evowork.enumeration.StatusType;
import com.evowork.viewmodel.common.PaginationResult;
import com.evowork.viewmodel.task.TaskFilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskRepositoryImpl extends AbstractRepository implements TaskRepositoryExtend {

    @Override
    public PaginationResult<Task> getListTask(TaskFilter filter, Long userId) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT * ");
        sql.append(" FROM task t ");
        sql.append(" LEFT JOIN users u on t.user_id = u.id ");
        sql.append(" WHERE t.user_id = :userId ");
        sql.append("  AND t.company_id = u.company_id ");
        sql.append("  AND t.status = :status ");
        parameters.put("userId", userId);
        parameters.put("status", StatusType.ACTIVE.getValue());
        if(filter.getMonth() != null) {
            sql.append("               AND to_char(t.scheduled_date at time zone 'Asia/Saigon' at time zone 'utc', 'YYYY-MM') = :month ");
            parameters.put("month", filter.getMonth());
        }
        if(filter.getDay() != null) {
            sql.append("               AND to_char(t.scheduled_date at time zone 'Asia/Saigon' at time zone 'utc', 'YYYY-MM-DD') = :createdAt ");
            parameters.put("createdAt", filter.getDay());
        }
        sql.append("  AND t.status = :status ");
        sql.append(" ORDER BY t.task_status ASC, t.scheduled_time DESC ");
        sql.append(getPagingSql(filter.getPaging()));
        PaginationResult<Task> result = new PaginationResult<>(filter.getPaging());
        List<Task> data = repo.getEntities(Task.class, sql.toString(), parameters);
        result.setData(data);
        return result;
    }
}
