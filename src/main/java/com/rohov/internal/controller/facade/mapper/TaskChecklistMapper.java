package com.rohov.internal.controller.facade.mapper;

import com.rohov.internal.controller.facade.dto.TaskChecklistDto;
import com.rohov.internal.entity.TaskChecklist;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TaskChecklistMapper {

    public TaskChecklistDto toDto(TaskChecklist taskChecklist) {
        return TaskChecklistDto.builder()
                .id(taskChecklist.getId())
                .value(taskChecklist.getValue())
                .isDone(taskChecklist.getIsDone())
                .build();
    }

    public TaskChecklist toEntity(TaskChecklistDto taskChecklistDto) {
        TaskChecklist taskChecklist = new TaskChecklist();

        taskChecklist.setId(taskChecklistDto.getId());
        taskChecklist.setValue(taskChecklistDto.getValue());
        taskChecklist.setIsDone(taskChecklistDto.getIsDone());

        return taskChecklist;
    }

}
