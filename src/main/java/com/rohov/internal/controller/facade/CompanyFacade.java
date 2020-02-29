package com.rohov.internal.controller.facade;

import com.rohov.internal.controller.facade.dto.CompanyDto;
import com.rohov.internal.controller.facade.dto.UserCompanyInviteDto;
import com.rohov.internal.controller.facade.dto.UserDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface CompanyFacade {

    List<CompanyDto> find(Pageable pageable);

    Set<CompanyDto> findByNameOrDescription(String value, Pageable pageable);

    List<CompanyDto> findOwned(Long managerId);

    List<CompanyDto> findMy(Long userId);

    List<UserDto> findEmployees(Long companyId);

    CompanyDto find(Long companyId);

    UserDto find(Long companyId, Long userId);

    CompanyDto create(Long ownerId, CompanyDto companyDto);

    CompanyDto update(Long companyId, CompanyDto companyDto);

    CompanyDto patch(Long companyId, CompanyDto companyDto);

    void delete(Long companyId);

    String addUser(Long companyId, UserCompanyInviteDto userCompanyInviteDto);


}
