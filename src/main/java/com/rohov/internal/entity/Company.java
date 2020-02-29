package com.rohov.internal.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.time.Instant;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Document(indexName = "company", type = "Company", shards = 1)
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    User owner;

    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;

    @Column(name = "created_date")
    Date createdDate = Date.from(Instant.now());

    @Column(name = "updated_date")
    Date updatedDate = Date.from(Instant.now());

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_company",
            joinColumns = { @JoinColumn (name = "company_id")},
            inverseJoinColumns = { @JoinColumn(name = "user_id")}
    )
    Set<User> users;

    @Column(name = "is_deleted")
    boolean isDeleted = false;
}
