package com.webapp.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class StaticFileHandler implements HttpHandler {
    private final String baseDir;

    public StaticFileHandler(String baseDir) {
        this.baseDir = baseDir;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        
        // Default to index.html if they just visit localhost:8080/
        if (path.equals("/")) {
            path = "/index.html";
        }
        
        File file = new File(baseDir + path).getCanonicalFile();
        
        // Security check: Prevent directory traversal (e.g., trying to access ../../passwords.txt)
        if (!file.getPath().startsWith(new File(baseDir).getCanonicalPath())) {
            exchange.sendResponseHeaders(403, -1);
            return;
        }

        if (!file.exists() || file.isDirectory()) {
            exchange.sendResponseHeaders(404, -1);
            return;
        }

        // Set the correct Content-Type so the browser knows if it's HTML, CSS, or JS
        String contentType = "text/plain";
        if (path.endsWith(".html")) contentType = "text/html";
        else if (path.endsWith(".css")) contentType = "text/css";
        else if (path.endsWith(".js")) contentType = "application/javascript";

        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.sendResponseHeaders(200, file.length());

        // Send the file contents to the browser
        try (OutputStream os = exchange.getResponseBody();
             FileInputStream fs = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            int count;
            while ((count = fs.read(buffer)) != -1) {
                os.write(buffer, 0, count);
            }
        }
    }
}
