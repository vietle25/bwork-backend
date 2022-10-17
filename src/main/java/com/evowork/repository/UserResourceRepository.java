package com.evowork.repository;

import com.evowork.entity.UserResource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserResourceRepository extends CrudRepository<UserResource, Long>, UserResourceRepositoryExtend {

    @Query(value = "select * from user_resource where user_id = :userId and type = :type", nativeQuery = true)
    List<UserResource> getUserResource(@Param("userId") Long userId, @Param("type") Integer type);

    @Query(value = "select * from user_resource where user_id = :userId and type = :type and image_side = :imageSide",
            nativeQuery = true)
    UserResource getUserResourceBySide(@Param("userId") Long userId,
                                       @Param("type") Integer type,
                                       @Param("imageSide") Integer imageSide);
}
