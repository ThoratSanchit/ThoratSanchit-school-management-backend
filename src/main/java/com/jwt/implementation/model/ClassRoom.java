package com.jwt.implementation.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ClassRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String className;   // e.g., "5", "6", "10"
    private String section;     // e.g., "A", "B"

    @ManyToOne
    private School school;

    @OneToOne
    private TeacherProfile classTeacher; // Optional: assign teacher as class incharge

}
