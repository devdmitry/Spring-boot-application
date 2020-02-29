package com.rohov.internal.controller;

import com.rohov.internal.controller.facade.CompanyFacade;
import com.rohov.internal.controller.facade.ProjectFacade;
import com.rohov.internal.controller.facade.TaskFacade;
import com.rohov.internal.controller.facade.UserFacade;
import com.rohov.internal.controller.facade.dto.TaskDto;
import com.rohov.internal.controller.facade.dto.UserDto;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@Api(tags = "task")
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
@RestController
@RequestMapping("/api/v1/projects/{projectId}/tasks")
public class TaskController {

    TaskFacade taskFacade;
    ProjectFacade projectFacade;
    CompanyFacade companyFacade;
    UserFacade userFacade;

    @GetMapping
    public ResponseEntity<List<TaskDto>> findAll(@RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "")
                                                             String token,
                                                 @PathVariable(name = "projectId") Long projectId) {
        if (isCompanyMember(token, projectId)) {
            return ResponseEntity.ok(taskFacade.findAll(projectId));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDto> find(@RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "")
                                                    String token,
                                        @PathVariable(name = "projectId") Long projectId,
                                        @PathVariable(name = "taskId") Long taskId) {
        if (isCompanyMember(token, projectId)) {
            return ResponseEntity.ok(taskFacade.findById(projectId, taskId));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping
    public ResponseEntity<TaskDto> create(@RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "")
                                                      String token,
                                          @PathVariable(name = "projectId") Long projectId,
                                          @Valid @RequestBody TaskDto taskDto) {
        if (isCompanyMember(token, projectId)) {
            return ResponseEntity.ok(taskFacade.create(projectId, taskDto));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDto> update(@RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "")
                                                      String token,
                                          @PathVariable(name = "projectId") Long projectId,
                                          @PathVariable(name = "taskId") Long taskId,
                                          @Valid @RequestBody TaskDto taskDto) {
        if (isCompanyMember(token, projectId)) {
            return ResponseEntity.ok(taskFacade.update(projectId, taskId, taskDto));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PatchMapping("/{taskId}")
    public ResponseEntity<TaskDto> patch(@RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "")
                                                  String token,
                                          @PathVariable(name = "projectId") Long projectId,
                                          @PathVariable(name = "taskId") Long taskId,
                                          @Valid @RequestBody TaskDto taskDto) {
        if (isCompanyMember(token, projectId)) {
            return ResponseEntity.ok(taskFacade.patch(projectId, taskId, taskDto));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<TaskDto> delete(@RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "")
                                                 String token,
                                         @PathVariable(name = "projectId") Long projectId,
                                         @PathVariable(name = "taskId") Long taskId) {
        if (isCompanyMember(token, projectId)) {
            taskFacade.delete(projectId, taskId);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/{taskId}/subscribers")
    public ResponseEntity<List<UserDto>> findSubscribers(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "") String token,
            @PathVariable(name = "projectId") Long projectId,
            @PathVariable(name = "taskId") Long taskId) {
        if (isCompanyMember(token, projectId)) {
            return ResponseEntity.ok(taskFacade.findSubscribers(projectId, taskId));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/{taskId}/subscribers")
    public ResponseEntity subscribe(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "") String token,
            @PathVariable(name = "projectId") Long projectId,
            @PathVariable(name = "taskId") Long taskId,
            @RequestBody List<Long> userIds) {
        if (isCompanyMember(token, projectId)) {
            taskFacade.subscribe(projectId, taskId, userIds);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("/{taskId}/subscribers")
    public ResponseEntity unsubscribe(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "") String token,
            @PathVariable(name = "projectId") Long projectId,
            @PathVariable(name = "taskId") Long taskId,
            @RequestBody List<Long> userIds) {
        if (isCompanyMember(token, projectId)) {
            taskFacade.unsubscribe(projectId, taskId, userIds);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    private boolean isCompanyMember(String token, Long projectId) {
        return Objects.nonNull(companyFacade.find(projectFacade.findCompanyByProject(projectId).getId(),
                userFacade.getByToken(token).getId()));
    }

}
