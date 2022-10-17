package com.evowork.repository;

import com.evowork.entity.UserSeenObject;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserSeenObjectRepository extends CrudRepository<UserSeenObject, Long>, UserSeenObjectRepositoryExtend {

    @Query(value = "select seen_at as id, table_name, table_id, user_id, seen_at from user_seen_object where " +
            "table_name = :tableName and table_id = :tableId and user_id = :userId",
            nativeQuery = true)
    UserSeenObject sellingVehicleSeenInUso(@Param("tableName") String tableName, @Param("tableId") Long tableId, @Param(
            "userId") Long userId);

    @Modifying()
    @Query(value = "insert into user_seen_object (user_id, table_name, table_id, seen_at) " +
            "values(:userId, :tableName, :tableId, now())",
            nativeQuery = true)
    int sellingVehicleSeenSave(@Param("tableName") String tableName, @Param("tableId") Long tableId, @Param(
            "userId") Long userId);

    @Modifying()
    @Query(value = "update user_seen_object set seen_at = now() where user_id = :userId " +
            "and table_name =:tableName and table_id = :tableId",
            nativeQuery = true)
    int sellingVehicleSeenUpdate(@Param("tableName") String tableName, @Param("tableId") Long tableId, @Param(
            "userId") Long userId);
}
