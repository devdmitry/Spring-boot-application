package com.rohov.internal.service.impl;

import com.rohov.internal.controller.facade.dto.UserDto;
import com.rohov.internal.entity.Project;
import com.rohov.internal.entity.Task;
import com.rohov.internal.entity.User;
import com.rohov.internal.exception.ErrorMessages;
import com.rohov.internal.exception.NotFoundException;
import com.rohov.internal.repository.jpa.TaskRepository;
import com.rohov.internal.service.ProjectService;
import com.rohov.internal.service.TaskService;
import com.rohov.internal.service.TaskStatusService;
import com.rohov.internal.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class TaskServiceImpl implements TaskService {

    TaskRepository taskRepository;

    ProjectService projectService;
    TaskStatusService taskStatusService;
    UserService userService;

    @Override
    public List<Task> findAll(Long projectId) {
        return taskRepository.findAllByProject_Id(projectId);
    }

    @Override
    public Task find(Long projectId, Long taskId) {
        final String errMsg = String.format(ErrorMessages.TASK_WRONG_ID, taskId);
        return taskRepository.findById(taskId)
                .filter(task -> task.getProject().getId().equals(projectId))
                .orElseThrow(() -> new NotFoundException(errMsg));
    }

    @Override
    public Task create(Long projectId, Task task) {
        Project project = projectService.findById(projectId);

        task.setProject(project);
        task.setStatus(taskStatusService.findByName(task.getStatus().getName()));

        return taskRepository.save(task);
    }

    @Override
    public Task update(Long projectId, Long taskId, Task task) {
        Task currentTask = find(projectId, taskId);

        currentTask.setUpdatedDate(Date.from(Instant.now()));
        currentTask.setName(task.getName());
        currentTask.setDescription(task.getDescription());
        currentTask.setStatus(taskStatusService.findByName(task.getStatus().getName()));

        return taskRepository.save(currentTask);
    }

    @Override
    public Task patch(Long projectId, Long taskId, Task task) {
        Task currentTask = find(projectId, taskId);

        currentTask.setUpdatedDate(Date.from(Instant.now()));
        if (Objects.nonNull(task.getName())) {
            currentTask.setName(task.getName());
        }
        if (Objects.nonNull(task.getDescription())) {
            currentTask.setDescription(task.getDescription());
        }
        if (Objects.nonNull(task.getStatus())) {
            currentTask.setStatus(taskStatusService.findByName(task.getStatus().getName()));
        }

        return taskRepository.save(currentTask);
    }

    @Override
    public void delete(Long projectId, Long taskId) {
        taskRepository.delete(find(projectId, taskId));
    }

    @Override
    public Set<User> findSubscribers(Long projectId, Long taskId) {
        Set<User> sub =  find(projectId, taskId).getSubscribers();
        return sub;
    }

    @Override
    public void subscribe(Long projectId, Long taskId, List<Long> userIds) {
        Task task = find(projectId, taskId);
        Set<User> users = new HashSet<>();
        User user;
        for (Long userId : userIds) {
            user = userService.findById(userId);
            if (task.getProject().getUsers().contains(user)) {
                if (!task.getSubscribers().contains(user)) {
                    users.add(user);
                }
            }
        }
        task.getSubscribers().addAll(users);
        taskRepository.save(task);
    }

    @Override
    public void unsubscribe(Long projectId, Long taskId, List<Long> userIds) {
        Task task = find(projectId, taskId);
        User user;
        for (Long userId : userIds) {
            user = userService.findById(userId);
            if (task.getSubscribers().contains(user)) {
                task.getSubscribers().remove(user);
            }
        }
        taskRepository.save(task);
    }
}
