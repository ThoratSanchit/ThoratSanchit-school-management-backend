package com.jwt.implementation.service.serviceImpl;

import com.jwt.implementation.model.SubjectAssignment;
import com.jwt.implementation.repository.SubjectAssignmentRepository;
import com.jwt.implementation.service.SubjectAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectAssignmentServiceImpl implements SubjectAssignmentService {

    @Autowired
    private SubjectAssignmentRepository subjectAssRepo;

    @Override
    public SubjectAssignment create(SubjectAssignment assignment) {
        return this.subjectAssRepo.save(assignment);
    }

    @Override
    public Object getAllAssignment() {
        return this.subjectAssRepo.findAll();
    }
}
