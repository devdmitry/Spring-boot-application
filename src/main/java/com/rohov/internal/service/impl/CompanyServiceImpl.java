package com.rohov.internal.service.impl;

import com.rohov.internal.entity.Company;
import com.rohov.internal.entity.Role;
import com.rohov.internal.entity.User;
import com.rohov.internal.entity.UserCompany;
import com.rohov.internal.exception.AlreadyExistException;
import com.rohov.internal.exception.ErrorMessages;
import com.rohov.internal.exception.NotFoundException;
import com.rohov.internal.repository.elastic.CompanyElasticRepository;
import com.rohov.internal.repository.jpa.CompanyRepository;
import com.rohov.internal.repository.jpa.UserCompanyRepository;
import com.rohov.internal.repository.jpa.UserRepository;
import com.rohov.internal.security.TokenManager;
import com.rohov.internal.service.CompanyService;
import com.rohov.internal.service.EmailService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class CompanyServiceImpl implements CompanyService {


    UserCompanyRepository userCompanyRepository;
    CompanyRepository companyRepository;
    CompanyElasticRepository companyElasticRepository;
    UserRepository userRepository;

    EmailService emailService;

    TokenManager tokenManager;

    @Override
    public List<Company> find(Pageable pageable) {
        return companyRepository.findAllByIsDeletedFalse(pageable);
    }

    @Override
    public List<Company> findOwned(Long managerId) {
        final String errMsg = String.format(ErrorMessages.USER_WRONG_ID, managerId);
        if (userRepository.findById(managerId).isPresent()) {
            return companyRepository.findAllByOwnerId(managerId);
        }
        throw new NotFoundException(errMsg);
    }

    @Override
    public List<Company> findMy(Long userId) {
        final String errMsg = String.format(ErrorMessages.USER_WRONG_ID, userId);
        if (userRepository.findById(userId).isPresent()) {
            return userCompanyRepository.findAllByUser_Id(userId)
                    .stream()
                    .map(userCompany -> userCompany.getCompany())
                    .collect(Collectors.toList());
        }
        throw new NotFoundException(errMsg);
    }

    @Override
    public List<User> findEmployees(Long companyId) {
        final String errMsg = String.format(ErrorMessages.COMPANY_WRONG_ID, companyId);
        if (companyRepository.findById(companyId).isPresent()) {
            return userCompanyRepository.findAllByCompany_Id(companyId)
                    .stream()
                    .map(userCompany -> userCompany.getUser())
                    .collect(Collectors.toList());
        }
        throw new NotFoundException(errMsg);
    }

    @Override
    public Company find(Long id) {
        final String errorMsg = String.format(ErrorMessages.COMPANY_WRONG_ID, id);
        return companyRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(errorMsg));
    }

    @Override
    public Company find(String name) {
        final String errorMsg = String.format(ErrorMessages.COMPANY_WRONG_NAME, name);
        return companyRepository
                .findByName(name)
                .orElseThrow(() -> new NotFoundException(errorMsg));
    }

    @Override
    public Set<Company> findByNameOrDescription(String value, Pageable pageable) {
        return companyElasticRepository.findAllByNameContainsOrDescriptionContains(value, value, pageable)
                .stream()
                .collect(Collectors.toSet());
    }

    @Override
    public User find(Long companyId, Long userId) {
        final String errorUserOrCompany = String.format(ErrorMessages.COMPANY_WRONG_ID, companyId);
        User user = userCompanyRepository.findByCompany_IdAndUser_Id(companyId, userId)
                .orElseThrow(() -> new NotFoundException(errorUserOrCompany)).getUser();
        return user;
    }

    @Transactional
    @Override
    public Company create(Long ownerId, Company company) {
        final String errorMsg = String.format(ErrorMessages.COMPANY_WITH_NAME_ALREADY_EXIST, company.getName());
        if (companyRepository.findByName(company.getName()).isPresent()) {
            throw new AlreadyExistException(errorMsg);
        }
        UserCompany userCompany = new UserCompany(userRepository.findById(ownerId).get(), company);
        company.setOwner(userCompany.getUser());
        company = companyRepository.save(company);
        companyElasticRepository.save(company);
        return userCompanyRepository.save(userCompany).getCompany();
    }

    @Override
    public Company update(Long id, Company company) {
        Company currentCompany = find(id);

        currentCompany.setName(company.getName());
        currentCompany.setDescription(company.getDescription());
        currentCompany.setUpdatedDate(company.getUpdatedDate());

        currentCompany = companyRepository.save(currentCompany);
        return companyElasticRepository.save(currentCompany);
    }

    @Override
    public Company patch(Long id, Company company) {
        Company currentCompany = find(id);

        if (company.getName() != null) {
            currentCompany.setName(company.getName());
        }
        if (company.getDescription() != null) {
            currentCompany.setDescription(company.getDescription());
        }

        currentCompany =  companyRepository.save(currentCompany);
        return companyElasticRepository.save(currentCompany);
    }

    @Override
    public void delete(Long id) {
        Company currentCompany = find(id);

        currentCompany.setUpdatedDate(Date.from(Instant.now()));
        currentCompany.setDeleted(true);

        companyElasticRepository.save(companyRepository.save(currentCompany));
    }

    @Override
    public String addUser(Long companyId, String email, List<Role> roles) {
        String token = tokenManager.createInvationToken(email, companyId, roles);
        //emailService.sendInvationToken(email, token);
        return token;
    }
}
