package com.rohov.internal.controller;

import com.rohov.internal.controller.facade.CompanyFacade;
import com.rohov.internal.controller.facade.UserFacade;
import com.rohov.internal.controller.facade.dto.UserCompanyInviteDto;
import com.rohov.internal.controller.facade.dto.UserDto;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@Api(tags = "user-company")
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
@RestController
@RequestMapping("/api/v1/companies/{companyId}/users")
public class UserCompanyController {

    CompanyFacade companyFacade;
    UserFacade userFacade;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "") String token,
            @PathVariable(name = "companyId") Long companyId) {
        if (isCompanyMember(token, companyId)) {
            return ResponseEntity.ok(companyFacade.findEmployees(companyId));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> get(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "") String token,
            @PathVariable(name = "companyId") Long companyId,
            @PathVariable(name = "id") Long userId) {
        if (isCompanyMember(token, companyId)) {
            return ResponseEntity.ok(companyFacade.find(companyId, userId));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping
    public ResponseEntity<String> addUser(@RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "") String token,
                                  @PathVariable(name = "companyId") Long companyId,
                                  @RequestBody UserCompanyInviteDto userCompanyInviteDto) {
        if (isCompanyMember(token, companyId)) {
            return ResponseEntity.ok(companyFacade.addUser(companyId, userCompanyInviteDto));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    private boolean isCompanyMember(String token, Long companyId) {
        return Objects.nonNull(companyFacade.find(companyId, userFacade.getByToken(token).getId()));
    }

}
