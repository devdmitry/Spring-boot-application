package com.rohov.internal.service.impl;

import com.rohov.internal.entity.Role;
import com.rohov.internal.exception.ErrorMessages;
import com.rohov.internal.exception.NotFoundException;
import com.rohov.internal.repository.jpa.RoleRepository;
import com.rohov.internal.service.RoleService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        final String errMsg = String.format(ErrorMessages.ROLE_WRONG_NAME, name);
        return roleRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException(errMsg));
    }
}
