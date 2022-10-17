package com.evowork.repository;

import com.evowork.entity.Notification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * @author tuanlt
 * @since 1.0
 */
public interface UserNotificationRepository extends CrudRepository<Notification, Long> {

    @Query(value = "select * from user_notification where 1=1 and notification_id = :notificationId and user_id " +
            "=:userId", nativeQuery = true)
    Notification getUserNotification(@Param("notificationId") Long notificationId, @Param("userId") Long
            userId);

    @Query(value = "SELECT   *  " +
            "FROM     notification  " +
            "WHERE    (search_text LIKE :paramSearch " +
            "OR       search_text_tsv @@ plainto_tsquery('pg_catalog.simple', :paramSearch)) " +
            "AND      user_id = :userId " +
            "ORDER BY created_at DESC ", nativeQuery = true)
    List<Notification> searchNotification(@Param("userId") Long userId, @Param("paramSearch") String stringSearch);

    @Query(value = "SELECT   * " +
            "FROM     notification " +
            "WHERE    (search_text LIKE :paramSearch " +
            "OR       search_text_tsv @@ plainto_tsquery('pg_catalog.simple', :paramSearch)) " +
            "AND      user_id = :userId " +
            "AND      type = :type " +
            "ORDER BY created_at DESC", nativeQuery = true)
    List<Notification> searchNotificationByType(@Param("userId") Long userId, @Param("type") Integer type,
                                                @Param("paramSearch") String stringSearch);
}
