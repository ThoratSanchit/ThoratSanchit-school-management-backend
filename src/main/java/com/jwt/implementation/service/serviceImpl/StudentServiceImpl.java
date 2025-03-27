package com.jwt.implementation.service.serviceImpl;

import com.jwt.implementation.repository.StudentRepository;
import com.jwt.implementation.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;
}
