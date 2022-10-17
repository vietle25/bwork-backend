package com.evowork.repository;

import com.evowork.entity.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long>, TaskRepositoryExtend {

    @Query(value = "select * from task where 1=1 and id = :taskId and status != :status ", nativeQuery = true)
    Task getTaskById(@Param("taskId") Long taskId, @Param("status") Integer delete);

    @Query(value = "select * from task where 1=1 " +
            " and user_id = :userId " +
            " and status != :status " +
            " and to_char(created_at at time zone 'Asia/Saigon' at time zone 'utc', 'YYYY-MM-DD') = :day ", nativeQuery = true)
    List<Task> getTaskByDay(@Param("userId") Long userId, @Param("day") String day, @Param("status") Integer delete);

}
