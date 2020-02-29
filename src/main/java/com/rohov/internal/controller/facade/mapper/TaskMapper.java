package com.rohov.internal.controller.facade.mapper;

import com.rohov.internal.controller.facade.dto.TaskDto;
import com.rohov.internal.entity.Task;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class TaskMapper {

    TaskStatusMapper taskStatusMapper;
    TaskChecklistMapper taskChecklistMapper;

    public TaskDto toDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .status(taskStatusMapper.toDto(task.getStatus()))
                .checklist(task.getChecklist()
                        .stream()
                        .map(taskChecklistMapper::toDto)
                        .collect(Collectors.toSet()))
                .completePercent(task.getChecklist().size() > 0 ?
                        (100 / task.getChecklist().size() * (float) task.getChecklist()
                                .stream()
                                .filter(taskChecklist -> taskChecklist.getIsDone())
                                .count()) : 100)
                .createdDate(task.getCreatedDate())
                .updatedDate(task.getUpdatedDate())
                .build();
    }

    public Task toEntity(TaskDto taskDto) {
        Task task = new Task();

        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskStatusMapper.toEntity(taskDto.getStatus()));

        return task;
    }

}
