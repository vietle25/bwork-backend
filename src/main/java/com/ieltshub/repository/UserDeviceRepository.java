package com.ieltshub.repository;

import com.ieltshub.entity.UserDevice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserDeviceRepository extends CrudRepository<UserDevice, Long>, UserDeviceRepositoryExtend {

    @Query(value = "select * from user_device where user_id = :userId and device_token = :token", nativeQuery = true)
    UserDevice getUserDevice(@Param("userId") Long userId, @Param("token") String token);

    @Query(value = "select count(*) num from user_device where user_id = :userId and device_token = :deviceToken",
            nativeQuery = true)
    Integer countUserDevice(@Param("userId") Long userId, @Param("deviceToken") String deviceToken);

    @Query(value = "delete from user_device where user_id = :userId", nativeQuery = true)
    UserDevice deleteUserDeviceByUserId(@Param("userId") Long userId);

    @Query(value = "select * from user_device where device_token = :deviceToken order by created_at desc limit 1",
            nativeQuery =
            true)
    UserDevice findRowExist(@Param("deviceToken") String deviceToken);
}
