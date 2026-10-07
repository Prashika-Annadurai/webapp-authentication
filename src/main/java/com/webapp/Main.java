package com.webapp;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.webapp.http.RegistrationHandler;
import com.webapp.repository.JdbcUserRepository;
import com.webapp.repository.UserRepository;
import com.webapp.service.RegistrationService;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting backend server...");

       
        UserRepository repository = new JdbcUserRepository();
        RegistrationService service = new RegistrationService(repository);
        RegistrationHandler handler = new RegistrationHandler(service);

        
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        
        
        server.createContext("/api/register", handler).getFilters().add(new Filter() {
            @Override
            public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
                exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
                
                
                if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                    exchange.sendResponseHeaders(204, -1);
                    return;
                }
                chain.doFilter(exchange);
            }
            @Override
            public String description() { return "CORS Filter"; }
        });
        
        
        server.setExecutor(null);
        server.start();

        System.out.println("Backend is running! Ready to receive registrations on http://localhost:8080");
    }
}

