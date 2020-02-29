package com.rohov.internal.controller.facade.impl;

import com.rohov.internal.controller.facade.TaskChecklistFacade;
import com.rohov.internal.controller.facade.dto.TaskChecklistDto;
import com.rohov.internal.controller.facade.dto.UserDto;
import com.rohov.internal.controller.facade.mapper.TaskChecklistMapper;
import com.rohov.internal.controller.facade.mapper.UserMapper;
import com.rohov.internal.service.TaskChecklistService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class TaskChecklistFacadeImpl implements TaskChecklistFacade {

    TaskChecklistService checklistService;

    UserMapper userMapper;
    TaskChecklistMapper taskChecklistMapper;

    @Override
    public List<TaskChecklistDto> findAllByTaskId(Long taskId) {
        return checklistService.finaAllByTask(taskId)
                .stream()
                .map(taskChecklistMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Set<UserDto> findCompanyUsersByTaskId(Long taskId) {
        return checklistService.findCompanyUsersByTaskId(taskId)
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public TaskChecklistDto add(Long taskId, TaskChecklistDto checklistDto) {
        return taskChecklistMapper.toDto(checklistService.add(taskId, taskChecklistMapper.toEntity(checklistDto)));
    }

    @Override
    public TaskChecklistDto markDone(Long taskId, Long checklistId) {
        return taskChecklistMapper.toDto(checklistService.markDone(taskId, checklistId));
    }

    @Override
    public void remove(Long taskId, Long checklistId) {
        checklistService.delete(taskId, checklistId);
    }
}
