package com.rohov.internal.repository.jpa;

import com.rohov.internal.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long>, PagingAndSortingRepository<Project, Long> {

    Page<Project> findAllByCompany_Id(Long companyId, Pageable pageable);

    Page<Project> findAllByCompany_IdAndNameContains(Long companyId, String name, Pageable pageable);

}
