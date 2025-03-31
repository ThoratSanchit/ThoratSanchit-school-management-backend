package com.jwt.implementation.service;

import com.jwt.implementation.model.Subject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectService {
   List<Subject> createSubjects(List<Subject> subjects);
}
