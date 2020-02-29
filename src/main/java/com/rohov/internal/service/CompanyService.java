package com.rohov.internal.service;

import com.rohov.internal.entity.Company;
import com.rohov.internal.entity.Role;
import com.rohov.internal.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface CompanyService {

    List<Company> find(Pageable pageable);

    List<Company> findOwned(Long managerId);

    List<Company> findMy(Long userId);

    List<User> findEmployees(Long companyId);

    Company find(Long companyId);

    User find(Long companyId, Long userId);

    Company find(String name);

    Set<Company> findByNameOrDescription(String value, Pageable pageable);

    Company create(Long ownerId, Company company);

    Company update(Long companyId, Company company);

    Company patch(Long companyId, Company company);

    void delete(Long companyId);

    String addUser(Long companyId, String email, List<Role> roles);

}
