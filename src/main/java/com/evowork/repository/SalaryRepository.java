package com.evowork.repository;

import com.evowork.entity.Salary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SalaryRepository extends CrudRepository<Salary, Long> {

    @Query(value = "select * from salary where user_id = :userId and period = :period", nativeQuery =
            true)
    Salary getSalaryByUserId(@Param("userId") Long userId, @Param("period") String period);
}