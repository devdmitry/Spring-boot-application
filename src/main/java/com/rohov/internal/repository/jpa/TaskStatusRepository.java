package com.rohov.internal.repository.jpa;

import com.rohov.internal.entity.TaskStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskStatusRepository extends CrudRepository<TaskStatus, Long> {

    Optional<TaskStatus> findByName(String name);

}
