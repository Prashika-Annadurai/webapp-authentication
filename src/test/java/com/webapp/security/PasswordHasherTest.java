package com.webapp.security;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.beans.Transient;

public class PasswordHasherTest {
    
    @Test 
    public void 
    testHashAndVerifyPassword(){
        String  originalPassword="mySuperSecretPassword123!";
        String hashedPassword = PasswordHasher.hash(originalPassword);

        assertNotEquals(originalPassword,hashedPassword);

        assertTrue(PasswordHasher.verify(originalPassword,hashedPassword));
        
        assertFalse(PasswordHasher.verify("wrongPassword",hashedPassword));
    }
    }