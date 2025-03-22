package com.jwt.implementation.service;

import com.jwt.implementation.model.Admin;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminService {
    Admin createAdmin(Admin admin);
}
