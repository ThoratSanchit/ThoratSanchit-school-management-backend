package com.jwt.implementation.repository;

import com.jwt.implementation.model.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolServiceRepository extends JpaRepository<School,Long> {
}
