package com.jwt.implementation.service;

import com.jwt.implementation.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserService {
    User createUser(User user);
}
