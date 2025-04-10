package com.jwt.implementation.service;

import com.jwt.implementation.model.ClassRoom;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRoomService {
    ClassRoom create(ClassRoom classRoom);
}
