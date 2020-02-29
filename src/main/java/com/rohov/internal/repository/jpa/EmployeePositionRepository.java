package com.rohov.internal.repository.jpa;

import com.rohov.internal.entity.EmployeePosition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeePositionRepository extends CrudRepository<EmployeePosition, Long>, PagingAndSortingRepository<EmployeePosition, Long> {

    Optional<EmployeePosition> findByName(String name);

}
