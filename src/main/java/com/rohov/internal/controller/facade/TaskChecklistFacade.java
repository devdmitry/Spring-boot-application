package com.rohov.internal.controller.facade;

import com.rohov.internal.controller.facade.dto.TaskChecklistDto;
import com.rohov.internal.controller.facade.dto.UserDto;

import java.util.List;
import java.util.Set;

public interface TaskChecklistFacade {

    List<TaskChecklistDto> findAllByTaskId(Long taskId);

    Set<UserDto> findCompanyUsersByTaskId(Long taskId);

    TaskChecklistDto add(Long taskId, TaskChecklistDto taskChecklistDto);

    TaskChecklistDto markDone(Long taskId, Long checklistId);

    void remove(Long taskId, Long checklistId);

}
