package com.rohov.internal.controller.facade;

import com.rohov.internal.controller.facade.dto.TaskDto;
import com.rohov.internal.controller.facade.dto.UserDto;

import java.util.List;

public interface TaskFacade {

    List<TaskDto> findAll(Long projectId);

    TaskDto findById(Long projectId, Long taskId);

    TaskDto create(Long projectId, TaskDto taskDto);

    TaskDto update(Long projectId, Long taskId, TaskDto taskDto);

    TaskDto patch(Long projectId, Long taskId, TaskDto taskDto);

    void delete(Long projectId, Long taskId);

    List<UserDto> findSubscribers(Long projectId, Long taskId);

    void subscribe(Long projectId, Long taskId, List<Long> userIds);

    void unsubscribe(Long projectId, Long taskId, List<Long> userIds);

}
