package com.jwt.implementation.service;

import com.jwt.implementation.model.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentService {

    Student createStudent(Student student);
}
