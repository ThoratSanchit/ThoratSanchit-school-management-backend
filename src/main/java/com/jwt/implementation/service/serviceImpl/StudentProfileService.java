package com.jwt.implementation.service.serviceImpl;

import com.jwt.implementation.model.StudentProfile;
import com.jwt.implementation.repository.StudentProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentProfileService implements com.jwt.implementation.service.StudentProfileService {
    @Autowired
    private StudentProfileRepository studentProfileRepository;

    @Override
    public StudentProfile createStudent(StudentProfile profile) {
        return studentProfileRepository.save(profile);
    }
}
