package com.rohov.internal.service.impl;

import com.rohov.internal.entity.TaskStatus;
import com.rohov.internal.exception.ErrorMessages;
import com.rohov.internal.exception.NotFoundException;
import com.rohov.internal.repository.jpa.TaskStatusRepository;
import com.rohov.internal.service.TaskStatusService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class TaskStatusServiceImpl implements TaskStatusService {

    TaskStatusRepository taskStatusRepository;

    @Override
    public TaskStatus findById(Long taskStatusId) {
        final String errMsg = String.format(ErrorMessages.TASK_STATUS_WRONG_ID, taskStatusId);
        return taskStatusRepository.findById(taskStatusId)
                .orElseThrow(() -> new NotFoundException(errMsg));

    }

    @Override
    public TaskStatus findByName(String taskStatusName) {
        final String errMsg = String.format(ErrorMessages.TASK_STATUS_WRONG_NAME, taskStatusName);
        return taskStatusRepository.findByName(taskStatusName)
                .orElseThrow(() -> new NotFoundException(errMsg));

    }
}
