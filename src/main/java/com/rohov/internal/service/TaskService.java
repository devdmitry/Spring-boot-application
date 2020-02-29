package com.rohov.internal.service;

import com.rohov.internal.entity.Task;
import com.rohov.internal.entity.User;

import java.util.List;
import java.util.Set;

public interface TaskService {

    List<Task> findAll(Long projectId);

    Task find(Long projectId, Long taskId);

    Task create(Long projectId, Task task);

    Task update(Long projectId, Long taskId, Task task);

    Task patch(Long projectId, Long taskId, Task task);

    void delete(Long projectId, Long taskId);

    Set<User> findSubscribers(Long projectId, Long taskId);

    void subscribe(Long projectId, Long taskId, List<Long> userIds);

    void unsubscribe(Long projectId, Long taskId, List<Long> userIds);

}
