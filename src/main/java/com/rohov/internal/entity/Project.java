package com.rohov.internal.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Collections;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "project")
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;

    @ManyToOne(targetEntity = Company.class)
    @JoinColumn(name = "company_id")
    Company company;

    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<UserProjectPermission> projectPermissions;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_project_permission",
            joinColumns = { @JoinColumn (name = "project_id")},
            inverseJoinColumns = { @JoinColumn(name = "user_id")}
    )
    Set<User> users = Collections.EMPTY_SET;

    @Column(name = "created_date")
    Instant createdDate = Instant.now();

    @Column(name = "updated_date")
    Instant updatedDate = Instant.now();

}
