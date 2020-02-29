package com.rohov.internal.controller.facade.impl;

import com.rohov.internal.controller.facade.TaskFacade;
import com.rohov.internal.controller.facade.dto.TaskDto;
import com.rohov.internal.controller.facade.dto.UserDto;
import com.rohov.internal.controller.facade.mapper.TaskMapper;
import com.rohov.internal.controller.facade.mapper.UserMapper;
import com.rohov.internal.service.TaskService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class TaskFacadeImpl implements TaskFacade {

    TaskService taskService;
    TaskMapper taskMapper;
    UserMapper userMapper;

    @Override
    public List<TaskDto> findAll(Long projectId) {
        return taskService.findAll(projectId)
                .stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto findById(Long projectId, Long taskId) {
        return taskMapper.toDto(taskService.find(projectId, taskId));
    }

    @Override
    public TaskDto create(Long projectId, TaskDto taskDto) {
        return taskMapper.toDto(taskService.create(projectId, taskMapper.toEntity(taskDto)));
    }

    @Override
    public TaskDto update(Long projectId, Long taskId, TaskDto taskDto) {
        return taskMapper.toDto(taskService.update(projectId, taskId, taskMapper.toEntity(taskDto)));
    }

    @Override
    public TaskDto patch(Long projectId, Long taskId, TaskDto taskDto) {
        return taskMapper.toDto(taskService.patch(projectId, taskId, taskMapper.toEntity(taskDto)));
    }

    @Override
    public void delete(Long projectId, Long taskId) {
        taskService.delete(projectId, taskId);
    }

    @Override
    public List<UserDto> findSubscribers(Long projectId, Long taskId) {
        List<UserDto> us = taskService.findSubscribers(projectId, taskId).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return us;
    }

    @Override
    public void subscribe(Long projectId, Long taskId, List<Long> userIds) {
        taskService.subscribe(projectId, taskId, userIds);
    }

    @Override
    public void unsubscribe(Long projectId, Long taskId, List<Long> userIds) {
        taskService.unsubscribe(projectId, taskId, userIds);
    }
}
