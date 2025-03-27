package com.jwt.implementation.repository;

import com.jwt.implementation.model.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassModelRepository  extends JpaRepository<ClassEntity,Long> {
}
