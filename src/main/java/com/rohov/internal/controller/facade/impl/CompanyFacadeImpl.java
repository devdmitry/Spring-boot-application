package com.rohov.internal.controller.facade.impl;

import com.rohov.internal.controller.facade.CompanyFacade;
import com.rohov.internal.controller.facade.dto.CompanyDto;
import com.rohov.internal.controller.facade.dto.UserCompanyInviteDto;
import com.rohov.internal.controller.facade.dto.UserDto;
import com.rohov.internal.controller.facade.mapper.CompanyMapper;
import com.rohov.internal.controller.facade.mapper.RoleMapper;
import com.rohov.internal.controller.facade.mapper.UserMapper;
import com.rohov.internal.service.CompanyService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class CompanyFacadeImpl implements CompanyFacade {

    CompanyService companyService;

    CompanyMapper companyMapper;
    RoleMapper roleMapper;
    UserMapper userMapper;

    @Override
    public List<CompanyDto> find(Pageable pageable) {
        return companyService.find(pageable)
                .stream()
                .map(companyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CompanyDto> findOwned(Long managerId) {
        return companyService.findOwned(managerId)
                .stream()
                .map(companyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CompanyDto> findMy(Long userId) {
        return companyService.findMy(userId)
                .stream()
                .map(companyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findEmployees(Long companyId) {
        return companyService.findEmployees(companyId)
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Set<CompanyDto> findByNameOrDescription(String value, Pageable pageable) {
        return companyService.findByNameOrDescription(value, pageable)
                .stream()
                .map(companyMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public CompanyDto find(Long companyId) {
        return companyMapper.toDto(companyService.find(companyId));
    }

    @Override
    public UserDto find(Long companyId, Long userId) {
        return userMapper.toDto(companyService.find(companyId, userId));
    }

    @Override
    public CompanyDto create(Long ownerId, CompanyDto companyDto) {
        return companyMapper.toDto(companyService.create(ownerId, companyMapper.toEntity(companyDto, true)));
    }

    @Override
    public CompanyDto update(Long companyId, CompanyDto companyDto) {
        return companyMapper.toDto(companyService.update(companyId, companyMapper.toEntity(companyDto, false)));
    }

    @Override
    public CompanyDto patch(Long companyId, CompanyDto companyDto) {
        return companyMapper.toDto(companyService.patch(companyId, companyMapper.toEntity(companyDto, false)));
    }

    @Override
    public void delete(Long companyId) {
        companyService.delete(companyId);
    }

    @Override
    public String addUser(Long companyId, UserCompanyInviteDto userCompanyInviteDto) {
        return companyService
                .addUser(
                    companyId,
                    userCompanyInviteDto.getEmail(),
                    userCompanyInviteDto.getRoles().stream().map(roleMapper::toEntity).collect(Collectors.toList()));
    }
}
