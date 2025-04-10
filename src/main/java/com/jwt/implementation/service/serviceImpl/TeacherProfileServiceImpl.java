package com.jwt.implementation.service.serviceImpl;

import com.jwt.implementation.repository.TeacherProfileRepository;
import com.jwt.implementation.service.TeacherProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherProfileServiceImpl implements TeacherProfileService {

    @Autowired
    private TeacherProfileRepository teacherProfileRepository;
}
