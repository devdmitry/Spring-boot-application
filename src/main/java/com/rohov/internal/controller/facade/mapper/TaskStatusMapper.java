package com.rohov.internal.controller.facade.mapper;

import com.rohov.internal.controller.facade.dto.TaskStatusDto;
import com.rohov.internal.entity.TaskStatus;
import org.springframework.stereotype.Component;

@Component
public class TaskStatusMapper {

    TaskStatusDto toDto(TaskStatus taskStatus) {
        return TaskStatusDto.builder()
                .id(taskStatus.getId())
                .name(taskStatus.getName())
                .build();
    }

    TaskStatus toEntity(TaskStatusDto taskStatusDto) {
        TaskStatus taskStatus = new TaskStatus();

        taskStatus.setName(taskStatusDto.getName());

        return taskStatus;
    }

}
