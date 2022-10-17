package com.evowork.repository;

import com.evowork.entity.UserSalaryConfig;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author tuannd
 * @date 12/03/2016
 * @since 1.0
 */
public interface UserSalaryConfigRepository extends CrudRepository<UserSalaryConfig, Long> {

    @Query(value = "select * from user_salary_config where user_id = :userId " +
            "and valid_from <= :validFrom order by valid_from desc limit 1",
            nativeQuery = true)
    UserSalaryConfig getUserSalaryConfigByUserId(@Param("userId") Long userId,
                                                 @Param("validFrom") String validFrom);

    @Query(value = "select * from user_salary_config where user_id = :userId " +
            "and valid_from > :month order by valid_from desc limit 1",
            nativeQuery = true)
    UserSalaryConfig getUserSalaryConfigExistByMonth(@Param("userId") Long userId,
                                                     @Param("month") String month);
}
