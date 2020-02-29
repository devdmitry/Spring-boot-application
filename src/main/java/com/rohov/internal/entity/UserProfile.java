package com.rohov.internal.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_profile")
public class UserProfile {

    @Id
    @Column(name = "id")
    Long id;

    @Column(name = "name")
    String name;

    @ManyToOne(targetEntity = EmployeePosition.class)
    @JoinColumn(name = "position_id")
    EmployeePosition employeePosition;

}
