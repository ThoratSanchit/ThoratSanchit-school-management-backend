package com.jwt.implementation.service;

import com.jwt.implementation.dto.StudentResponseDto;
import com.jwt.implementation.model.StudentProfile;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentProfileService {
    StudentProfile createStudent(StudentProfile profile);
    StudentResponseDto findStudentProfileByUserId(Long userId);

}
