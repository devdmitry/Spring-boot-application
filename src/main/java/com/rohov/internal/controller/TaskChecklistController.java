package com.rohov.internal.controller;


import com.rohov.internal.controller.facade.TaskChecklistFacade;
import com.rohov.internal.controller.facade.UserFacade;
import com.rohov.internal.controller.facade.dto.TaskChecklistDto;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Api(tags = "task_checklist")
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
@RestController
@RequestMapping("/api/v1/projects/{projectId}/tasks/{taskId}/checklist")
public class TaskChecklistController {

    TaskChecklistFacade taskChecklistFacade;
    UserFacade userFacade;

    @GetMapping
    public ResponseEntity<List<TaskChecklistDto>> findAllByTaskId(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "") String token,
            @PathVariable(name = "taskId") Long taskId) {
        if (isCompanyMember(token, taskId)) {
            return ResponseEntity.ok(taskChecklistFacade.findAllByTaskId(taskId));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping
    public ResponseEntity<TaskChecklistDto> add(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "") String token,
            @PathVariable(name = "taskId") Long taskId,
            @Valid @RequestBody TaskChecklistDto taskChecklistDto) {
        if (isCompanyMember(token, taskId)) {
            return ResponseEntity.ok(taskChecklistFacade.add(taskId, taskChecklistDto));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PatchMapping("/{checklistId}")
    public ResponseEntity<TaskChecklistDto> markDone(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "") String token,
            @PathVariable(name = "taskId") Long taskId,
            @PathVariable(name = "checklistId") Long checklistId) {
        if (isCompanyMember(token, taskId)) {
            return ResponseEntity.ok(taskChecklistFacade.markDone(taskId, checklistId));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("/{checklistId}")
    public ResponseEntity<TaskChecklistDto> delete(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "") String token,
            @PathVariable(name = "taskId") Long taskId,
            @PathVariable(name = "checklistId") Long checklistId) {
        if (isCompanyMember(token, taskId)) {
            taskChecklistFacade.remove(taskId, checklistId);
            ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    private boolean isCompanyMember(String token, Long taskId) {
        return taskChecklistFacade.findCompanyUsersByTaskId(taskId).contains(userFacade.getByToken(token));
    }


}
