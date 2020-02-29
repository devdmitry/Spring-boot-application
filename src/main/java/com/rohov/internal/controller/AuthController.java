package com.rohov.internal.controller;

import com.rohov.internal.controller.facade.dto.UserCompleteProfileDto;
import com.rohov.internal.controller.facade.dto.UserCredentialDto;
import com.rohov.internal.controller.facade.dto.UserDto;
import com.rohov.internal.controller.facade.dto.UserNewPasswordDto;
import com.rohov.internal.controller.facade.dto.UserResetPasswordDto;
import com.rohov.internal.controller.facade.dto.UserResponseDto;
import com.rohov.internal.controller.facade.impl.UserFacadeImpl;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import static lombok.AccessLevel.PRIVATE;

@Api(tags = "authentication")
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    UserFacadeImpl userFacade;

    @PostMapping("/sign-in")
    public ResponseEntity<UserResponseDto> signIn(@Valid @RequestBody UserCredentialDto userCredentialDto,
                                                  HttpServletResponse response) {
        return ResponseEntity.ok(userFacade.signIn(userCredentialDto, response));
    }

    @PostMapping("/reset-password")
    public ResponseEntity resetPassword(@Valid @RequestBody UserResetPasswordDto userResetPasswordDto) {
        userFacade.sendResetToken(userResetPasswordDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/new-password")
    public ResponseEntity<UserResponseDto> newPassword(@Valid @RequestBody UserNewPasswordDto userNewPasswordDto) {
        return ResponseEntity.ok(userFacade.newPassword(userNewPasswordDto));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserDto> signUp(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userFacade.create(userDto));
    }

    @PutMapping("/email-verification")
    public ResponseEntity emailVerification(@PathParam(value = "verificationToken") String verificationToken) {
        userFacade.verificateUser(verificationToken);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/complete-profile")
    public ResponseEntity<UserDto> completeProfile(@RequestBody UserCompleteProfileDto userCompleteProfileDto) {
        return ResponseEntity.ok(userFacade.completeProfile(userCompleteProfileDto));
    }
}
