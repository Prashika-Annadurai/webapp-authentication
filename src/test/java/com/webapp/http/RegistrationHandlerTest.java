package com.webapp.http;

import com.sun.net.httpserver.HttpServer;
import com.webapp.model.User;
import com.webapp.repository.UserRepository;
import com.webapp.service.RegistrationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationHandlerTest {

    private HttpServer server;
    private HttpClient client;

    @BeforeEach
    public void setup() throws IOException {
       
        UserRepository fakeRepo = new UserRepository() {
            @Override
            public void save(User user) {}
            
            @Override
            public boolean emailExists(String email) {
                return "exists@example.com".equals(email);
            }
        };

        RegistrationService service = new RegistrationService(fakeRepo);
        
        
        server = HttpServer.create(new InetSocketAddress(0), 0);
        server.createContext("/api/register", new RegistrationHandler(service));
        server.setExecutor(null);
        server.start();
        
        client = HttpClient.newHttpClient();
    }

    @AfterEach
    public void teardown() {
        server.stop(0);
    }

    @Test
    public void testSuccessfulRegistration() throws Exception {
        String json = "{\"name\":\"Alice\",\"phone\":\"1234\",\"email\":\"alice@example.com\",\"password\":\"pass\"}";
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + server.getAddress().getPort() + "/api/register"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
                
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
      
        assertEquals(201, response.statusCode());
    }

    @Test
    public void testDuplicateEmail() throws Exception {
        String json = "{\"name\":\"Bob\",\"phone\":\"1234\",\"email\":\"exists@example.com\",\"password\":\"pass\"}";
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + server.getAddress().getPort() + "/api/register"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
                
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        
        assertEquals(400, response.statusCode());
    }
}
