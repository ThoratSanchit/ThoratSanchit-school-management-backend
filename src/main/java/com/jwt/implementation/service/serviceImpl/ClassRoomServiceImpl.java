package com.jwt.implementation.service.serviceImpl;

import com.jwt.implementation.model.ClassRoom;
import com.jwt.implementation.repository.ClassRoomRepository;
import com.jwt.implementation.service.ClassRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassRoomServiceImpl implements ClassRoomService {

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Override
    public ClassRoom create(ClassRoom classRoom) {
        return this.classRoomRepository.save(classRoom);
    }
}
