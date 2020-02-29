package com.rohov.internal.repository.jpa;

import com.rohov.internal.entity.UserProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends CrudRepository<UserProfile, Long>,
        PagingAndSortingRepository<UserProfile, Long> {
}
