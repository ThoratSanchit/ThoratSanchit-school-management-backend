package com.jwt.implementation.service.serviceImpl;

import com.jwt.implementation.model.Admin;
import com.jwt.implementation.repository.AdminRepository;
import com.jwt.implementation.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;
    @Override
    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }
}
