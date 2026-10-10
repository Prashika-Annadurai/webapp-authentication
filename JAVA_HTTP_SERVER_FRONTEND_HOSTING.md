# Java HTTP Server Static Frontend Hosting

## 1. Phase Overview and Objective
This phase transitions the application from relying on file-system browsing (using the `file://` protocol) to a true web-server model. The objective is to extend the standard Java `HttpServer` to serve static frontend assets (HTML, CSS, JavaScript) directly via `http://localhost:8080/`.

## 2. Previous Registration Implementation Context
Prior to this phase, the application successfully implemented a User Registration flow. The frontend forms communicated with the backend via `fetch()`, hitting the `POST /api/register` endpoint. That feature was fully tested end-to-end, passed all automated tests, and successfully persisted records to a MySQL database using JDBC and PBKDF2 hashing.

## 3. Limitation of the Previous Workflow
Previously, the user had to open `frontend/index.html` directly from their operating system. While functional for local API development using CORS, this approach does not simulate real-world web hosting, prevents proper relative routing, and requires users to manually locate files on their hard drive rather than simply visiting a URL.

## 4. StaticFileHandler Implementation and Responsibilities
To resolve this limitation without introducing external frameworks, a custom `StaticFileHandler` was created. 
Its responsibilities are:
- **Routing:** Accept requests mapping to frontend resources.
- **Root Resolution:** Automatically map requests for the root path `/` to `/index.html`.
- **Security:** Ensure requested files are strictly within the designated `frontend` directory by verifying canonical paths (preventing path traversal attacks).
- **MIME Types:** Inspect file extensions and set appropriate `Content-Type` headers (`text/html`, `text/css`, `application/javascript`).
- **File Streaming:** Read file bytes using `FileInputStream` and stream them back to the client via the HTTP response body.

## 5. Changes to Main.java and Routing
`Main.java` was updated to import and instantiate the new `StaticFileHandler`. A new context was registered with the active `HttpServer`:
```java
server.createContext("/", new StaticFileHandler("frontend"));
```
This directs all incoming HTTP requests that are not explicitly matched by more specific contexts to the static file handler.

## 6. How Assets are Served
When the browser requests a file (e.g., `style.css`), the handler concatenates the configured base directory (`frontend`) with the requested path. It verifies existence and security constraints, explicitly sets the MIME type based on the extension, sets a `200 OK` status, and streams the binary data over the network socket.

## 7. Expected Browser Workflow
Users can now open any web browser and navigate directly to `http://localhost:8080/`. The Java server will resolve this to `frontend/index.html` and return the complete User Registration UI.

## 8. Coexistence with API Routes
The `HttpServer` uses exact and prefix matching for contexts. Because the registration endpoint is registered at `/api/register`, it takes precedence for those specific requests. The static handler registered at `/` serves as a fallback for all other URIs, allowing seamless coexistence of REST API endpoints and static file serving on the same port.

## 9. Stopping and Restarting the Server
Because Java requires recompilation after source file modifications, the running server must be halted and restarted. 
- Stop command: `CTRL + C` (in the active terminal window)
- Start command: `/c/Gradle/gradle-8.10.2/bin/gradle run`

## 10. 404 Error, Investigation, and Resolution Status
- **Reported Error:** The user attempted to load `http://localhost:8080/` and received an HTTP 404 (Not Found) error.
- **Investigation:** Inspection of the terminal state revealed that the original `gradle run` process had been executing for over 35 minutes.
- **Root Cause:** The server was running the *old* compiled bytecode from before `StaticFileHandler` was added to `Main.java`. By default, Java `HttpServer` returns 404 for any unregistered context.
- **Resolution Status:** Unresolved pending user action. The server must be explicitly stopped and restarted to load the newly compiled static route mapping.

## 11. Tests and Verification Evidence
- **Registration End-to-End Flow:** Verified successfully in the previous phase (3 passing JUnit tests, real MySQL insertion proven).
- **Static File Serving:** **NOT VERIFIED**. While the source code (`StaticFileHandler.java` and `Main.java`) has been inspected and appears structurally correct for root resolution, MIME typing, and security, actual execution has not occurred because the server process was never restarted.

## 12. Known Limitations and Next Steps
- The MIME type detection is currently hardcoded and limited to `.html`, `.css`, and `.js`. Other assets (images, fonts, JSON files) will default to `text/plain` unless explicitly mapped.
- Next Step: Restart the Java server and re-test the `http://localhost:8080/` URL in a browser.
