package com.jwt.implementation.service.serviceImpl;

import com.jwt.implementation.model.Teacher;
import com.jwt.implementation.repository.TeacherRepository;
import com.jwt.implementation.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;
    @Override
    public Teacher saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }
}
