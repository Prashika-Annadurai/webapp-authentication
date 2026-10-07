package com.webapp.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.webapp.service.RegistrationService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class RegistrationHandler implements HttpHandler {
    
    private final RegistrationService registrationService;
    
    public RegistrationHandler(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        
        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendResponse(exchange, 405, "{\"error\":\"Method Not Allowed\"}");
            return;
        }

        try {
            
            InputStream is = exchange.getRequestBody();
            String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            
        
            String name = extractJsonField(body, "name");
            String phone = extractJsonField(body, "phone");
            String email = extractJsonField(body, "email");
            String password = extractJsonField(body, "password");
            
            
            boolean success = registrationService.registerUser(name, phone, email, password);
            
           
            if (success) {
                sendResponse(exchange, 201, "{\"message\":\"Registration successful!\"}");
            } else {
                
                sendResponse(exchange, 400, "{\"error\":\"Invalid input or email already exists.\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "{\"error\":\"Internal server error\"}");
        }
    }
    
    private void sendResponse(HttpExchange exchange, int statusCode, String responseText) throws IOException {
        byte[] bytes = responseText.getBytes(StandardCharsets.UTF_8);
        
        
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        
        
        exchange.sendResponseHeaders(statusCode, bytes.length);
        
        
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

   
    private String extractJsonField(String json, String field) {
        String key = "\"" + field + "\":\"";
        int start = json.indexOf(key);
        if (start == -1) return null;
        start += key.length();
        int end = json.indexOf("\"", start);
        if (end == -1) return null;
        return json.substring(start, end);
    }
}
