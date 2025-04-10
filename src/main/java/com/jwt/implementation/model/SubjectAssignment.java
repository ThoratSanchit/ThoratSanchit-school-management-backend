package com.jwt.implementation.model;

import lombok.Data;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
public class SubjectAssignment {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private TeacherProfile teacher;

    @ManyToOne
    private Subject subject;

    @ManyToOne
    private ClassRoom classRoom;
}