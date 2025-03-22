package com.jwt.implementation.repository;

import com.jwt.implementation.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository  extends JpaRepository<Admin,Long> {
}
