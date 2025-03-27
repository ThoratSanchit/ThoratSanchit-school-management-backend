package com.jwt.implementation.service;

import com.jwt.implementation.model.ClassEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassModelService {
    ClassEntity createClass(ClassEntity classEntity);
}
