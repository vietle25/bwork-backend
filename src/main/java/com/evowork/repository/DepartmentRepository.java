package com.evowork.repository;

import com.evowork.entity.Department;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepartmentRepository extends CrudRepository<Department, Long>, DepartmentRepositoryExtend {

    @Query(value = "select d.* " +
            "from department_staff df " +
            "join company c on df.company_id = c.id " +
            "join department d on df.department_id = d.id " +
            "where df.user_id = :userId " +
            "and c.status = :status " +
            "and df.status = :status " +
            "and d.status = :status ",
            nativeQuery = true)
    Department getDepartment(@Param("userId") Long userId,
                             @Param("status") Integer status);

    @Query(value = "select d.* " +
            "from department d " +
            "where d.company_id = :companyId " +
            "and d.status = :status " +
            "order by d.created_at desc",
            nativeQuery = true)
    List<Department> getDepartments(@Param("companyId") Long companyId,
                                    @Param("status") Integer status);

}
