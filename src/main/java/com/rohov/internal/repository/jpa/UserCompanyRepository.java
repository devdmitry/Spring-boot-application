package com.rohov.internal.repository.jpa;

import com.rohov.internal.entity.UserCompany;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCompanyRepository extends CrudRepository<UserCompany, Long> {

    List<UserCompany> findAllByUser_Id(Long userId);

    List<UserCompany> findAllByCompany_Id(Long companyId);

    Optional<UserCompany> findByCompany_IdAndUser_Id(Long companyId, Long userId);

}
