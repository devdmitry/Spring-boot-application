package com.rohov.internal.repository.elastic;

import com.rohov.internal.entity.Company;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyElasticRepository extends ElasticsearchCrudRepository<Company, Long>,
        PagingAndSortingRepository<Company, Long> {

    List<Company> findAllByNameContainsOrDescriptionContains(String name, String description, Pageable pageable);

}
