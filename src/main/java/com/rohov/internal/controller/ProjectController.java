package com.rohov.internal.controller;

import com.rohov.internal.controller.facade.CompanyFacade;
import com.rohov.internal.controller.facade.ProjectFacade;
import com.rohov.internal.controller.facade.UserFacade;
import com.rohov.internal.controller.facade.dto.ProjectDto;
import com.rohov.internal.controller.facade.dto.RoleDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@Api(tags = "project")
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
@RestController
@RequestMapping("/api/v1")
public class ProjectController {

    ProjectFacade projectFacade;
    CompanyFacade companyFacade;
    UserFacade userFacade;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "String", paramType = "query", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "String", paramType = "query", defaultValue = "20"),
            @ApiImplicitParam(name = "sort", dataType = "String", paramType = "query")
    })
    @GetMapping("/companies/{companyId}/projects")
    public ResponseEntity<List<ProjectDto>> getAll(@RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "")
                                                               String token,
                                                   @PathVariable(name = "companyId") Long companyId,
                                                   @RequestParam(required = false) String name,
                                                   Pageable pageable) {
        if (isMember(token, companyId)) {
            return ResponseEntity.ok(projectFacade.findAll(companyId, name, pageable));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/companies/{companyId}/projects/{id}")
    public ResponseEntity<ProjectDto> getById(@RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "")
                                                          String token,
                                              @PathVariable(name = "companyId") Long companyId,
                                              @PathVariable(name = "id") Long id) {
        if (isMember(token, companyId)) {
            return ResponseEntity.ok(projectFacade.find(companyId, id));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/companies/{companyId}/projects")
    public ResponseEntity<ProjectDto> create(@RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "")
                                                         String token,
                                             @PathVariable(name = "companyId") Long companyId,
                                             @Valid @RequestBody ProjectDto projectDto) {
        if (isMember(token, companyId)) {
            return ResponseEntity.ok(projectFacade.create(companyId, projectDto));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PutMapping("/companies/{companyId}/projects/{id}")
    public ResponseEntity<ProjectDto> update(@RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "")
                                                         String token,
                                             @PathVariable(name = "companyId") Long companyId,
                                             @PathVariable(name = "id") Long id,
                                             @Valid @RequestBody ProjectDto projectDto) {
        if (isMember(token, companyId)) {
            return ResponseEntity.ok(projectFacade.update(companyId, id, projectDto));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PatchMapping("/companies/{companyId}/projects/{id}")
    public ResponseEntity<ProjectDto> patch(@RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "")
                                                        String token,
                                            @PathVariable(name = "companyId") Long companyId,
                                            @PathVariable(name = "id") Long id,
                                            @RequestBody ProjectDto projectDto) {
        if (isMember(token, companyId)) {
            return ResponseEntity.ok(projectFacade.patch(companyId, id, projectDto));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("/companies/{companyId}/projects/{id}")
    public ResponseEntity delete(@RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "")
                                             String token,
                                 @PathVariable(name = "companyId") Long companyId,
                                 @PathVariable(name = "id") Long id) {
        if (isMember(token, companyId)) {
            projectFacade.delete(companyId, id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/{projectId}/users/{userId}/permissions")
    public ResponseEntity addUserProjectPermission(@RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "")
                                                               String token,
                                                   @PathVariable(name = "projectId") Long projectId,
                                                   @PathVariable(name = "userId") Long userId,
                                                   @RequestBody List<RoleDto> permissions) {
        if (isMember(token, projectFacade.findCompanyByProject(projectId).getId())) {
            projectFacade.addUserProjectPermission(projectFacade.findCompanyByProject(projectId).getId(),
                    projectId, userId, permissions);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    private boolean isMember(String token, Long companyId) {
        return Objects.nonNull(companyFacade.find(companyId, userFacade.getByToken(token).getId()));
    }

}
