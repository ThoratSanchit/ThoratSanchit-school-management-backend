package com.jwt.implementation.model;

import javax.persistence.*;

import com.jwt.implementation.enums.UserType;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "parents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parentId;

    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String contactNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType typeOfUser = UserType.PARENT;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}