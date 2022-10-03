package com.ieltshub.repository;

import com.ieltshub.entity.Notification;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


/**
 * @author tuannd
 * @date 12/03/2016
 * @since 1.0
 */
public interface NotificationRepository extends CrudRepository<Notification, Long>, NotificationRepositoryExtend {

    @Query(value = "SELECT COUNT(is_seen) as number_new_notification " +
            "FROM notification WHERE (is_seen = false or is_seen is null) " +
            "and user_id = :userId", nativeQuery = true)
    Integer countNewNotification(@Param("userId") Long userId);

    @Query(value = "select * from notification where user_id = :userId and is_seen = false", nativeQuery = true)
    List<Notification> getAllNotificationNotSeenById(@Param("userId") Long userId);
}
