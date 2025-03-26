package com.jwt.implementation.service;

import com.jwt.implementation.model.Teacher;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherService {
    Teacher saveTeacher(Teacher teacher);
}
