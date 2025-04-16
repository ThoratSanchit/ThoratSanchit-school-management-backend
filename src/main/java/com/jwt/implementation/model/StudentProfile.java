package com.jwt.implementation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class StudentProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentProfileId;

    @ManyToOne
    private ClassRoom classRoom;
    private String rollNumber;

    @OneToOne
    @JsonIgnore
    private User user; // if student login needed
}

