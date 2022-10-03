package com.ieltshub.repository;

import com.ieltshub.entity.Branch;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BranchRepository extends CrudRepository<Branch, Long>, BranchRepositoryExtend {

    @Query(value = "select b.* from branch b join user_branch ub on b.id = ub.branch_id " +
            "where b.company_id = :companyId and ub.user_id = :userId and b.status = :status " +
            "and ub.status = :status limit 1",
            nativeQuery = true)
    Branch getBranch(@Param("companyId") Long companyId,
                     @Param("userId") Long userId,
                     @Param("status") Integer status);

    @Query(value = "SELECT * FROM branch WHERE company_id = :companyId and status = :status",
            nativeQuery = true)
    List<Branch> getBranches(@Param("companyId") Long companyId,
                             @Param("status") Integer status);

    @Query(value = "select * from branch  where company_id = :companyId and admin_user_id = :adminUserId " +
            "and status = :status limit 1",
            nativeQuery = true)
    Branch getBranchByAdminUserId(@Param("companyId") Long companyId,
                                  @Param("adminUserId") Long userId,
                                  @Param("status") Integer status);
}
