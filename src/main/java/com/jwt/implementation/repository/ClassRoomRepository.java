package com.jwt.implementation.repository;

import com.jwt.implementation.model.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRoomRepository extends JpaRepository<ClassRoom,Long> {
}
