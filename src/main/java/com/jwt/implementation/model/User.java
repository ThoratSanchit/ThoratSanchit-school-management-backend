package com.jwt.implementation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user") // Assuming the table name is "user", adjust if different
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
    @Column(name = "address")
    private String address;
    @JsonFormat(pattern = "MMMM d, yyyy")
    private LocalDate dateOfBirth;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column
    private String phone;

    @ManyToOne
    @JoinColumn(name = "school_id")  // database madhe `school_id` column yenar
    private School school;
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist // Called automatically before saving to DB   and set the createdAt date automatically
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    // Enum for roles
    public enum Role {
        ADMIN, TEACHER, STUDENT, PARENT, ACCOUNTANT
    }

}