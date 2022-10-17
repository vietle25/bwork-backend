package com.evowork.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.evowork.entity.Notification;
import com.evowork.viewmodel.common.PaginationResult;
import com.evowork.viewmodel.common.Paging;

/**
 * NotificationSchedule repository
 *
 * @author tuannd
 * @date 15/10/2016
 * @since 1.0
 */
public class NotificationRepositoryImpl extends AbstractRepository implements NotificationRepositoryExtend {

    @Override
    public PaginationResult<Notification> getNotifications(Long userId, Integer type, Paging paging) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT * ");
        sql.append("FROM   notification ");
        sql.append("WHERE  user_id = :userId ");
        if (type != null && type != 0) {
            sql.append("AND type = :type ");
            parameters.put("type", type);
        }
//        sql.append("AND status = :status ");
        sql.append("ORDER BY created_at desc ");
        parameters.put("userId", userId);
//        parameters.put("status", StatusType.ACTIVE.getValue());
        sql.append(getPagingSql(paging));
        PaginationResult<Notification> result = new PaginationResult<>(paging);
        List<Notification> data = repo.getEntities(Notification.class, sql.toString(), parameters);
        result.setData(data);
        return result;
    }
}
