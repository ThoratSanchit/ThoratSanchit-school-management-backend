package com.jwt.implementation.service.serviceImpl;

import com.jwt.implementation.model.School;
import com.jwt.implementation.repository.SchoolServiceRepository;
import com.jwt.implementation.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private SchoolServiceRepository schoolRepository;

    @Override
    @Transactional
    public School ctreateSchool(School school) {
        // Save school first
       return schoolRepository.save(school);

//        // Save each admin with the correct school reference
//        for (Admin admin : school.getAdmins()) {
//            admin.setSchool(savedSchool);
//            adminRepository.save(admin);
//        }

//        return savedSchool;
    }
}
