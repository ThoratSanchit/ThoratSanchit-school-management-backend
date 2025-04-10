package com.jwt.implementation.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class TeacherProfile {
    @Id @GeneratedValue
    private Long id;

    @OneToOne
    private User user;

    @ManyToOne
    private School school;

    private String qualification;
    private String department;

    private LocalDate joiningDate;
}