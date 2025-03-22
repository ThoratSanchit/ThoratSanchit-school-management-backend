package com.jwt.implementation.service;

import com.jwt.implementation.model.School;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolService {
    School ctreateSchool(School school);
}
