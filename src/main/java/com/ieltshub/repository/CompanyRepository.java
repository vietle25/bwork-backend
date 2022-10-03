package com.ieltshub.repository;

import com.ieltshub.entity.Company;
import com.ieltshub.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CompanyRepository extends CrudRepository<Company, Long>, CompanyRepositoryExtend {

    @Query(value = "select * from company where 1=1 and upper(code) = upper(:code) and status = :status limit 1", nativeQuery = true)
    Company getCompanyByCode(@Param("code") String code, @Param("status") Integer active);
}
