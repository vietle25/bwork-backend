package com.evowork.repository;

import com.evowork.entity.UserBranch;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserBranchRepository extends CrudRepository<UserBranch, Long> {

    @Query(value = "select * from user_branch where user_id = :userId and status = :status limit 1",
            nativeQuery = true)
    UserBranch getUserBranchByUserId(@Param("userId") Long userId, @Param("status") Integer status);
}
