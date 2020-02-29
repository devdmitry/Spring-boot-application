package com.rohov.internal.service.impl;

import com.rohov.internal.entity.Task;
import com.rohov.internal.entity.TaskChecklist;
import com.rohov.internal.entity.User;
import com.rohov.internal.exception.ErrorMessages;
import com.rohov.internal.exception.NotFoundException;
import com.rohov.internal.repository.jpa.TaskChecklistRepository;
import com.rohov.internal.repository.jpa.TaskRepository;
import com.rohov.internal.service.TaskChecklistService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Set;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class TaskChecklistServiceImpl implements TaskChecklistService {

    TaskRepository taskRepository;
    TaskChecklistRepository taskChecklistRepository;

    @Override
    public Set<TaskChecklist> finaAllByTask(Long taskId) {
        return taskChecklistRepository.findAllByTask_Id(taskId);
    }

    @Override
    public Set<User> findCompanyUsersByTaskId(Long taskId) {
        final String errMsg = String.format(ErrorMessages.TASK_WRONG_ID, taskId);
        return taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException(errMsg))
                .getProject()
                .getCompany()
                .getUsers();
    }

    @Override
    public TaskChecklist add(Long taskId, TaskChecklist taskChecklist) {
        final String errMsg = String.format(ErrorMessages.TASK_WRONG_ID, taskId);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException(errMsg));
        taskChecklist.setTask(task);
        taskChecklist.setIsDone(false);
        return taskChecklistRepository.save(taskChecklist);
    }

    @Override
    public TaskChecklist markDone(Long taskId, Long checklistId) {
        TaskChecklist taskChecklist = findById(checklistId);
        if (taskChecklist.getTask().getId().equals(taskId)) {
            taskChecklist.setIsDone(true);
        }
        return taskChecklistRepository.save(taskChecklist);
    }

    @Override
    public void delete(Long taskId, Long ckecklistId) {
        TaskChecklist checklist = findById(ckecklistId);
        if (checklist.getTask().getId().equals(taskId)) {
            taskChecklistRepository.delete(checklist);
        }
    }

    private TaskChecklist findById(Long checklistId) {
        final String errMsg = String.format(ErrorMessages.TASK_CHECKLIST_WRONG_ID, checklistId);
        return taskChecklistRepository.findById(checklistId)
                .orElseThrow(() -> new NotFoundException(errMsg));
    }

}
