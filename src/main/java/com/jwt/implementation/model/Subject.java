package com.jwt.implementation.model;

import javax.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "subjects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subjectId;

    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private ClassEntity classEntity;

    @Column(nullable = false)
    private String subjectName;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
