package com.rohov.internal.repository.jpa;

import com.rohov.internal.entity.TaskChecklist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TaskChecklistRepository extends CrudRepository<TaskChecklist, Long> {

    Set<TaskChecklist> findAllByTask_Id(Long taskId);

}
