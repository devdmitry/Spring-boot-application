package com.rohov.internal.repository.jpa;

import com.rohov.internal.entity.Company;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long>, PagingAndSortingRepository<Company, Long> {

    List<Company> findAllByIsDeletedFalse(Pageable pageable);

    Optional<Company> findByName(String name);

    List<Company> findAllByOwnerId(Long managerId);

}
