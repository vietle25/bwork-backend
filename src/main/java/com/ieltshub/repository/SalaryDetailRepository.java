package com.ieltshub.repository;

import com.ieltshub.entity.SalaryDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalaryDetailRepository extends CrudRepository<SalaryDetail, Long>, SalaryDetailRepositoryExtend {

    @Query(value = "select * from salary_detail where user_id = :user_id and salary_id = :salary_id and status = :status",
            nativeQuery = true
    )
    List<SalaryDetail> getSalaryDetailById(@Param("user_id") Long user_id,
                                           @Param("salary_id") Long salary_id,
                                           @Param("status") Integer status);

    @Query(value = "select * from salary_detail where user_id = :user_id and salary_id = :salary_id and type = :typeSalary and status = :status", nativeQuery = true)
    SalaryDetail getSalaryDetailByType(@Param("user_id") Long user_id, @Param("salary_id") Long salary_id, @Param("type") Integer typeSalary, @Param("status") Integer status);
}