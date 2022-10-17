package com.evowork.repository;

import com.evowork.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long>, CategoryRepositoryExtend {

    @Query(value = "select * from category where type = :type and status = :status and company_id = :companyId " +
            "order by seq nulls last, name", nativeQuery =
            true)
    List<Category> getCategories(@Param("type") int type,
                                 @Param("status") int status,
                                 @Param("companyId") Long companyId);
}