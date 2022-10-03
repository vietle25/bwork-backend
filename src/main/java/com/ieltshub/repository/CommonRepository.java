package com.ieltshub.repository;
import com.ieltshub.entity.AppVersion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author tuannd
 * @date 12/03/2016
 * @since 1.0
 */
public interface CommonRepository extends CrudRepository<AppVersion, Long>, CommonRepositoryExtend {

    @Query(value = "select * from app_version where upper(name) = upper(:platform) " +
            "and status = 1 and app_type = :appType limit 1",
           nativeQuery =
            true)
    AppVersion getUpdateVersion(@Param("platform") String platform,
                                @Param("appType") Integer appType);
}
