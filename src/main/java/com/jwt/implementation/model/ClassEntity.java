package com.jwt.implementation.model;

import javax.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "classes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classId;

    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @Column(nullable = false, unique = true)
    private String className;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "classEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subject> subjects;
}
