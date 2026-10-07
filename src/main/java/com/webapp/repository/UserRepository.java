package com.webapp.repository;

import com.webapp.model.User;

public interface UserRepository {
    boolean emailExists(String email);
    void save(User user);
}
