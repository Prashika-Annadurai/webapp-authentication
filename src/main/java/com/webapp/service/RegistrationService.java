package com.webapp.service;

import com.webapp.model.User;
import com.webapp.repository.UserRepository;
import com.webapp.security.PasswordHasher;

public class RegistrationService {
    private final UserRepository repository;
    
    public RegistrationService(UserRepository repository) {
        this.repository = repository;
    }
    
    public boolean registerUser(String name, String phone, String email, String plainPassword) {
       
        if (name == null || name.trim().isEmpty()) return false;
        if (phone == null || phone.trim().isEmpty()) return false;
        if (email == null || email.trim().isEmpty() || !email.contains("@")) return false;
        if (plainPassword == null || plainPassword.trim().isEmpty()) return false;
        
       
        if (repository.emailExists(email)) {
            return false;
        }
        
        
        String hashedPassword = PasswordHasher.hash(plainPassword);
        
        
        User user = new User(name, phone, email, hashedPassword);
        repository.save(user);
        
        return true;
    }
}
