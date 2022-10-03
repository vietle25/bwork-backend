package com.ieltshub.repository;

import com.ieltshub.entity.UserLoginLog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author tuannd
 * @date 12/03/2016
 * @since 1.0
 */
public interface UserLoginLogRepository extends CrudRepository<UserLoginLog, Long> {

    @Query(value = "select ull.* from user_login_log ull " +
            "inner join users u " +
            "on ull.user_id = u.id " +
            "where ull.user_id = :userId " +
            "and u.status = :status", nativeQuery = true)
    List<UserLoginLog> getUserLoginLog(@Param("userId") Long userId, @Param("status") Integer status);

    @Query(value = "select ull.* from user_login_log ull " +
            "inner join users u " +
            "on ull.user_id = u.id " +
            "where ull.device_id = :deviceId " +
            "and u.status = :status", nativeQuery = true)
    List<UserLoginLog> getDeviceLoginLog(@Param("deviceId") String deviceId,
                                         @Param("status") Integer status);

    @Query(value = "select ull.* from user_login_log ull " +
            "inner join users u " +
            "on ull.user_id = u.id " +
            "where ull.device_id = :deviceId " +
            "and ull.user_id = :userId " +
            "and u.status = :status limit 1", nativeQuery = true)
    UserLoginLog getUserDeviceLogin(@Param("deviceId") String deviceId,
                                    @Param("userId") Long userId,
                                    @Param("status") Integer status);
}
