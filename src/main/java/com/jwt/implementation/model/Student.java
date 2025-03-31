package com.jwt.implementation.model;

import javax.persistence.*;

import com.jwt.implementation.enums.UserType;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

//    @ManyToOne
//    @JoinColumn(name = "parent_id", nullable = false)
//    private Parent parent;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private ClassEntity className;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String contactNumber;

    @Column(nullable = false)
    private LocalDateTime admissionDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType typeOfUser = UserType.STUDENT;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}