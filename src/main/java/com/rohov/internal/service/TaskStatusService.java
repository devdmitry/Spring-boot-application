package com.rohov.internal.service;

import com.rohov.internal.entity.TaskStatus;

public interface TaskStatusService {

    TaskStatus findById(Long taskStatusId);

    TaskStatus findByName(String name);

}
