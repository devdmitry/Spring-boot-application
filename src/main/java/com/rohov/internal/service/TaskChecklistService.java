package com.rohov.internal.service;

import com.rohov.internal.entity.TaskChecklist;
import com.rohov.internal.entity.User;

import java.util.List;
import java.util.Set;

public interface TaskChecklistService {

    Set<TaskChecklist> finaAllByTask(Long taskId);

    Set<User> findCompanyUsersByTaskId(Long taskId);

    TaskChecklist add(Long taskId, TaskChecklist taskChecklist);

    TaskChecklist markDone(Long taskId, Long checklistId);

    void delete(Long taskId, Long ckecklistId);

}
