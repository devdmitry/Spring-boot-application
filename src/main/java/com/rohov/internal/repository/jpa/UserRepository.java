package com.rohov.internal.repository.jpa;

import com.rohov.internal.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByResetToken(String token);

    Optional<User> findByVerificationToken(String token);

}
