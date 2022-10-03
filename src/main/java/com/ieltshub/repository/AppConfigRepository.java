package com.ieltshub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ieltshub.entity.AppConfig;
import org.springframework.data.repository.query.Param;


/**
 * @author tuannd
 * @date 12/03/2016
 * @since 1.0
 */
public interface AppConfigRepository extends CrudRepository<AppConfig, Long>, AppConfigRepositoryExtend {

    @Query(value = "select * from app_config where upper(name) like upper(:configName) and version = 0 " +
            "and company_id = :companyId and branch_id isnull limit 1",
            nativeQuery = true)
    AppConfig getConfigByName(@Param("configName") String configName,
                              @Param("companyId") Long companyId);

    @Query(value = "select * from app_config where upper(name) like upper(:configName) and version = 0 " +
            "and company_id = :companyId and branch_id = :branchId limit 1",
            nativeQuery = true)
    AppConfig getConfigByName(@Param("configName") String configName,
                              @Param("companyId") Long companyId,
                              @Param("branchId") Long branchId);

    @Query(value = "select * from app_config where name " +
            "in(:minuteBeforeCheckIn1,:minuteBeforeCheckIn2,:minuteAfterCheckOut1,:minuteAfterCheckOut2," +
            ":minuteAfterCheckIn1,:minuteAfterCheckIn2,:minuteBeforeCheckOut1,:minuteBeforeCheckOut2) " +
            "and company_id = :companyId and branch_id isnull and version = 0",
            nativeQuery = true)
    List<AppConfig> getConfigTimeAbleTimekeeping(
            @Param("minuteBeforeCheckIn1") String minuteBeforeCheckIn1,
            @Param("minuteBeforeCheckIn2") String minuteBeforeCheckIn2,
            @Param("minuteAfterCheckOut1") String minuteAfterCheckOut1,
            @Param("minuteAfterCheckOut2") String minuteAfterCheckOut2,
            @Param("minuteAfterCheckIn1") String minuteAfterCheckIn1,
            @Param("minuteAfterCheckIn2") String minuteAfterCheckIn2,
            @Param("minuteBeforeCheckOut1") String minuteBeforeCheckOut1,
            @Param("minuteBeforeCheckOut2") String minuteBeforeCheckOut2,
            @Param("companyId") Long companyId
    );

    @Query(value = "select * from app_config where name " +
            "in(:minuteBeforeCheckIn1,:minuteBeforeCheckIn2,:minuteAfterCheckOut1,:minuteAfterCheckOut2," +
            ":minuteAfterCheckIn1,:minuteAfterCheckIn2,:minuteBeforeCheckOut1,:minuteBeforeCheckOut2) " +
            "and company_id = :companyId and branch_id = :branchId and version = 0",
            nativeQuery = true)
    List<AppConfig> getConfigTimeAbleTimekeeping(
            @Param("minuteBeforeCheckIn1") String minuteBeforeCheckIn1,
            @Param("minuteBeforeCheckIn2") String minuteBeforeCheckIn2,
            @Param("minuteAfterCheckOut1") String minuteAfterCheckOut1,
            @Param("minuteAfterCheckOut2") String minuteAfterCheckOut2,
            @Param("minuteAfterCheckIn1") String minuteAfterCheckIn1,
            @Param("minuteAfterCheckIn2") String minuteAfterCheckIn2,
            @Param("minuteBeforeCheckOut1") String minuteBeforeCheckOut1,
            @Param("minuteBeforeCheckOut2") String minuteBeforeCheckOut2,
            @Param("companyId") Long companyId,
            @Param("branchId") Long branchId
    );
}
