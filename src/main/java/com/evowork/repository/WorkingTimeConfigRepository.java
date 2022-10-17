package com.evowork.repository;

import com.evowork.entity.WorkingTimeConfig;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface WorkingTimeConfigRepository extends CrudRepository<WorkingTimeConfig, Long>,
        WorkingTimeConfigRepositoryExtend {

    @Query(value = "select * from working_time_config where user_id = :user_id " +
            "and to_char(created_at, 'YYYY-MM') <= :month and company_id = :companyId " +
            "order by valid_from desc limit 1",
            nativeQuery = true)
    WorkingTimeConfig getWorkingTimeConfigByMonth(@Param("user_id") Long user_id,
                                                  @Param("month") String month,
                                                  @Param("companyId") Long companyId);

    @Query(value = "select * from working_time_config where user_id = :user_id " +
            "and valid_from >= :day and company_id = :companyId " +
            "order by valid_from desc limit 1",
            nativeQuery = true)
    WorkingTimeConfig getWorkingTimeConfigExistByDay(@Param("user_id") Long user_id,
                                                @Param("day") String day,
                                                @Param("companyId") Long companyId);
}
