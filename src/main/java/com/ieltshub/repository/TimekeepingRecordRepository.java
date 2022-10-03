package com.ieltshub.repository;

import com.ieltshub.entity.TimekeepingRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * @author tuannd
 * @date 12/03/2016
 * @since 1.0
 */
public interface TimekeepingRecordRepository extends CrudRepository<TimekeepingRecord, Long>, TimekeepingRecordRepositoryExtend {

    @Query(value = "select * from timekeeping_record " +
            "where user_id = :userId and status = :status and check_in_time notnull and check_out_time isnull " +
            "and to_char(created_at at time zone 'Asia/Saigon' at time zone 'utc', 'YYYY-MM-DD') = :createdAt " +
            "order by created_by desc limit 1",
            nativeQuery = true)
    TimekeepingRecord getTimekeepingCheckIn(
            @Param("userId") Long userId,
            @Param("status") Integer active,
            @Param("createdAt") String createdAt);

    @Query(value = "select * from timekeeping_record " +
            "where user_id = :userId and status = :status and check_in_time = :checkInTime and check_out_time notnull " +
            "and to_char(created_at at time zone 'Asia/Saigon' at time zone 'utc', 'YYYY-MM-DD') = :createdAt " +
            "order by created_by desc limit 1",
            nativeQuery = true)
    TimekeepingRecord getTimekeepingCheckInExact(
            @Param("checkInTime") String checkInTime,
            @Param("userId") Long userId,
            @Param("status") Integer active,
            @Param("createdAt") String createdAt);

    @Query(value = "select * from timekeeping_record " +
            "where user_id = :userId and status = :status and check_in_time notnull and check_out_time = :checkOutTime " +
            "and to_char(created_at at time zone 'Asia/Saigon' at time zone 'utc', 'YYYY-MM-DD') = :createdAt " +
            "order by created_by desc limit 1",
            nativeQuery = true)
    TimekeepingRecord getTimekeepingCheckOutExact(
            @Param("checkOutTime") String checkOutTime,
            @Param("userId") Long userId,
            @Param("status") Integer active,
            @Param("createdAt") String createdAt);

    @Query(value = "select * from timekeeping_record " +
            "where user_id = :userId and status = :status " +
            "and to_char(created_at at time zone 'Asia/Saigon' at time zone 'utc', 'YYYY-MM-DD') = :createdAt " +
            "order by check_in_time asc",
            nativeQuery = true)
    List<TimekeepingRecord> getTimekeeping(
            @Param("userId") Long userId,
            @Param("status") Integer active,
            @Param("createdAt") String createdAt);

    @Query(value = "select to_char(created_at at time zone 'Asia/Saigon' at time zone 'utc', 'YYYY-MM-DD') " +
            "as day_of_month from timekeeping_record where user_id = :userId " +
            "and to_char(created_at at time zone 'Asia/Saigon' at time zone 'utc', 'YYYY-MM') = :month " +
            "group by to_char(created_at at time zone 'Asia/Saigon' at time zone 'utc', 'YYYY-MM-DD') " +
            "order by day_of_month desc",
            nativeQuery = true)
    List<String> getDayOfMonth(@Param("userId") Long userId, @Param("month") String month);
}
