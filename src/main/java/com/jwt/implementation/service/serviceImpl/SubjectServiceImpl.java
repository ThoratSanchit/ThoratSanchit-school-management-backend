package com.jwt.implementation.service.serviceImpl;

import com.jwt.implementation.model.Subject;
import com.jwt.implementation.repository.SubjectRepository;
import com.jwt.implementation.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public List<Subject> createSubjects(List<Subject> subjects) {
        return subjectRepository.saveAll(subjects);
    }
}
