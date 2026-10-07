package com.webapp.service;

import com.webapp.model.User;
import com.webapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RegistrationServiceTest {

    
    static class FakeUserRepository implements UserRepository {
        boolean wasSaved = false;
        
        @Override
        public void save(User user) {
            wasSaved = true;
        }
        
        @Override
        public boolean emailExists(String email) {
            return "existing@example.com".equals(email);
        }
    }

    @Test
    public void testSuccessfulRegistration() {
        FakeUserRepository repo = new FakeUserRepository();
        RegistrationService service = new RegistrationService(repo);
        
        boolean result = service.registerUser("Alice", "1234567890", "alice@example.com", "password123");
        
        assertTrue(result);
        assertTrue(repo.wasSaved);
    }
    
    @Test
    public void testFailsOnMissingData() {
        FakeUserRepository repo = new FakeUserRepository();
        RegistrationService service = new RegistrationService(repo);
        
        assertFalse(service.registerUser("", "1234567890", "test@test.com", "pass"));
        assertFalse(service.registerUser("Alice", null, "test@test.com", "pass"));
    }

    @Test
    public void testFailsOnDuplicateEmail() {
        FakeUserRepository repo = new FakeUserRepository();
        RegistrationService service = new RegistrationService(repo);
        
        
        assertFalse(service.registerUser("Bob", "123", "existing@example.com", "pass"));
    }
}
