package com.rohov.internal.repository.jpa;

import com.rohov.internal.entity.UserProjectPermission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProjectPermissionRepository extends CrudRepository<UserProjectPermission, Long> {
}
