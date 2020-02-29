package com.rohov.internal.controller;

import com.rohov.internal.controller.facade.UserFacade;
import com.rohov.internal.controller.facade.dto.UserDto;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PRIVATE;


@Api(tags = "user")
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    UserFacade userFacade;

    @GetMapping
    public ResponseEntity<UserDto> get(@RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "") String token) {
        return ResponseEntity.ok(userFacade.getByToken(token));
    }

}
