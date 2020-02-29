package com.rohov.internal.service.impl;

import com.rohov.internal.entity.Permission;
import com.rohov.internal.exception.ErrorMessages;
import com.rohov.internal.exception.NotFoundException;
import com.rohov.internal.repository.jpa.PermissionRepository;
import com.rohov.internal.service.PermissionService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Service
public class PermissionServiceImpl implements PermissionService {

    PermissionRepository permissionRepository;

    @Override
    public Permission findByName(String name) {
        final String errMsg = String.format(ErrorMessages.PERMISSION_WRONG_NAME, name);
        return permissionRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException(errMsg));
    }
}
