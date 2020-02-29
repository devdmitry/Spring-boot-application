package com.rohov.internal.controller.facade.mapper;

import com.rohov.internal.controller.facade.dto.CompanyDto;
import com.rohov.internal.entity.Company;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Instant;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Component
public class CompanyMapper {

    UserMapper userMapper;

    public CompanyDto toDto(Company company) {
        return CompanyDto.builder()
                .id(company.getId())
                .name(company.getName())
                .description(company.getDescription())
                .updatedDate(company.getUpdatedDate())
                .createdDate(company.getCreatedDate())
                .owner(userMapper.toDto(company.getOwner()))
                .build();
    }

    public Company toEntity(CompanyDto companyDto, Boolean isCreate) {
        Company company = new Company();

        company.setName(companyDto.getName());
        company.setDescription(companyDto.getDescription());
        company.setCreatedDate(companyDto.getCreatedDate());
        company.setUpdatedDate(Date.from(Instant.now()));

        if(isCreate) {
            company.setCreatedDate(Date.from(Instant.now()));
        }

        return company;
    }
}
