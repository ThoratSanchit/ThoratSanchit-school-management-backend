package com.jwt.implementation.repository;

import com.jwt.implementation.model.SubjectAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectAssignmentRepository extends JpaRepository<SubjectAssignment,Long> {
}
