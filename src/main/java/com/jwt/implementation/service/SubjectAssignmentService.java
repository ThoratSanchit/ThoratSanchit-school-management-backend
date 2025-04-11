package com.jwt.implementation.service;

import com.jwt.implementation.model.SubjectAssignment;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectAssignmentService {
    SubjectAssignment create(SubjectAssignment assignment);

    Object getAllAssignment();
}
