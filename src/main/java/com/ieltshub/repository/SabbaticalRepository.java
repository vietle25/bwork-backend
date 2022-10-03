package com.ieltshub.repository;

import com.ieltshub.entity.Sabbatical;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;

public interface SabbaticalRepository extends CrudRepository<Sabbatical, Long>,
        SabbaticalRepositoryExtend {

    @Query(value = "select count(*) from sabbatical where user_id = :userId and status = :status " +
            "and approval_status = :approvalStatus " +
            "and to_char(off_from_date, 'YYYY-MM') = :monthSelected",
            nativeQuery = true)
    Integer countSabbaticalApprovedByMonth(@Param("userId") Long userId,
                                           @Param("status") Integer status,
                                           @Param("approvalStatus") Integer approvalStatus,
                                           @Param("monthSelected") String monthSelected);
}
