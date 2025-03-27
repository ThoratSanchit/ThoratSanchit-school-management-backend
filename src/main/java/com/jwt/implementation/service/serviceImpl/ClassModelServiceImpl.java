package com.jwt.implementation.service.serviceImpl;

import com.jwt.implementation.model.ClassEntity;
import com.jwt.implementation.repository.ClassModelRepository;
import com.jwt.implementation.service.ClassModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassModelServiceImpl implements ClassModelService {

    @Autowired
    private ClassModelRepository classModelRepository;

    @Override
    public ClassEntity createClass(ClassEntity classEntity) {
        return this.classModelRepository.save(classEntity);
    }
}
