package com.jwt.implementation.service.serviceImpl;

import com.jwt.implementation.dto.StudentResponseDto;
import com.jwt.implementation.model.ClassRoom;
import com.jwt.implementation.model.StudentProfile;
import com.jwt.implementation.model.User;
import com.jwt.implementation.repository.ClassRoomRepository;
import com.jwt.implementation.repository.StudentProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentProfileService implements com.jwt.implementation.service.StudentProfileService {
    @Autowired
    private StudentProfileRepository studentProfileRepository;

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Override
    public StudentProfile createStudent(StudentProfile profile) {
        return studentProfileRepository.save(profile);
    }

    @Override
    public StudentResponseDto findStudentProfileByUserId(Long userId) {
        Optional<StudentProfile> studentProfile = studentProfileRepository.findByUserId(userId);
        if (studentProfile.isPresent()) {
            StudentProfile profile = studentProfile.get();

            User user = profile.getUser();
            ClassRoom classRoom=profile.getClassRoom();
            Optional<ClassRoom> className= this.classRoomRepository.findById(classRoom.getId());

            StudentResponseDto student = new StudentResponseDto();
            student.setAddress(user.getAddress());
            student.setName(user.getName());
            student.setEmail(user.getEmail());
            student.setRollNumber(profile.getRollNumber());
            student.setPhone(user.getPhone());
            student.setDateOfBirth(user.getDateOfBirth());
            if(className.isPresent()){
                ClassRoom data=className.get();
                student.setClassRoomName(data.getClassName());
            }
            return student;
        } else {
            throw new RuntimeException("Student not found for user ID " + userId);
        }
    }
}
