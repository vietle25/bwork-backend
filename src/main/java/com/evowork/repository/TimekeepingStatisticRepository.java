package com.evowork.repository;

import com.evowork.entity.TimekeepingStatistic;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author tuannd
 * @date 12/03/2016
 * @since 1.0
 */
public interface TimekeepingStatisticRepository extends CrudRepository<TimekeepingStatistic, Long> {

    @Query(value = "select * from timekeeping_statistic where user_id = :userId and day = :day limit 1",
            nativeQuery = true)
    TimekeepingStatistic getTimekeepingStatistic(
            @Param("userId") Long userId,
            @Param("day") String day);
}
