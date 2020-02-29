package com.rohov.internal.repository.jpa;

import com.rohov.internal.entity.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findAllByProject_Id(Long projectId);

    Optional<Task> findById(Long id);

}
