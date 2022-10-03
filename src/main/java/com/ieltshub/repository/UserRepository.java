package com.ieltshub.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ieltshub.entity.User;

import java.util.List;

/**
 * @author tuannd
 * @date 12/03/2016
 * @since 1.0
 */
public interface UserRepository extends CrudRepository<User, Long>, UserRepositoryExtend {

    @Query(value = "select * from users where upper(email) = upper(:email) and status != :status and type = :type",
            nativeQuery = true)
    User getUserByEmail(@Param("email") String email,
                        @Param("status") Integer delete,
                        @Param("type") Integer type);

    @Query(value = "select * from users where 1=1 and upper(phone) = upper(:phone) and status != :status and type = :type",
            nativeQuery = true)
    User getUserByPhone(@Param("phone") String phone,
                        @Param("status") Integer delete,
                        @Param("type") Integer type);

    @Query(value = "select * from users where upper(email) = upper(:email) and status != :status", nativeQuery = true)
    User getUserByEmail(@Param("email") String email, @Param("status") Integer delete);

    @Query(value = "select * from users where 1=1 and upper(phone) = upper(:phone) and status != :status", nativeQuery = true)
    User getUserByPhone(@Param("phone") String phone, @Param("status") Integer delete);
}
