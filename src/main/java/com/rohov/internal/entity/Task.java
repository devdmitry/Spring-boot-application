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
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task")
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;

    @OneToMany(targetEntity = TaskChecklist.class, mappedBy = "task", fetch = FetchType.EAGER)
    Set<TaskChecklist> checklist;

    @ManyToOne(targetEntity = TaskStatus.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    TaskStatus status;

    @ManyToOne(targetEntity = Project.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    Project project;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "task_subscriber",
            joinColumns = { @JoinColumn (name = "task_id")},
            inverseJoinColumns = { @JoinColumn(name = "user_id")}
    )
    Set<User> subscribers;

    @Column(name = "created_date")
    Date createdDate = Date.from(Instant.now());

    @Column(name = "updated_date")
    Date updatedDate = Date.from(Instant.now());

}
