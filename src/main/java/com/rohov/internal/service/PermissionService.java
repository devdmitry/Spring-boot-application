package com.rohov.internal.service;

import com.rohov.internal.entity.Permission;

public interface PermissionService {

    Permission findByName(String name);

}
