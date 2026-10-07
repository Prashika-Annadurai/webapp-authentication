# Phase 1: Project Boilerplate / Initial Framework-Free Web Application Setup

## 1. Phase Title and Purpose
**Phase 1 Objective:** Establish the initial project boilerplate including Gradle configuration, Java structure, frontend structure, database script structure, and basic build verification. 
**Important Note on Repository State:** The provided historical context indicates Phase 1 deferred all registration and HTTP functionality. However, the current repository state shows that Phase 2 (User Registration) has already been fully implemented. In accordance with instructions, the **current repository state is treated as authoritative** for what actually exists, and this contradiction is explicitly documented below.

## 2. Starting State
The project was initially empty and built from scratch.

## 3. Requirements and Constraints
- Architecture must remain completely framework-free.
- No application frameworks (no Spring, React, Angular, Hibernate).
- Keep frontend, backend, and database responsibilities cleanly separated.

## 4. Technologies Used
- Frontend: HTML, CSS, Vanilla JavaScript
- Backend: Plain Java, Java standard HTTP server
- Database: MySQL, JDBC
- Build: Gradle

## 5. Architecture
- Frontend: Static HTML/CSS/JS communicating via HTTP
- Backend: Standard Java `HttpServer` and manual JDBC connections
- Database: MySQL executing schema SQL

## 6. Project Structure (Current Authoritative State)
```text
Webapp-authentication/
├── build.gradle
├── settings.gradle
├── database/
│   └── schema.sql
├── frontend/
│   ├── app.js
│   ├── index.html
│   └── style.css
└── src/
    ├── main/java/com/webapp/
    │   ├── Main.java
    │   ├── http/RegistrationHandler.java
    │   ├── model/User.java
    │   ├── repository/JdbcUserRepository.java
    │   ├── repository/UserRepository.java
    │   ├── security/PasswordHasher.java
    │   └── service/RegistrationService.java
    └── test/java/com/webapp/
        ├── http/RegistrationHandlerTest.java
        ├── security/PasswordHasherTest.java
        └── service/RegistrationServiceTest.java
```

## 7. Files Created
- `settings.gradle`
- `build.gradle`
- `src/main/java/com/webapp/Main.java`
- `frontend/index.html`
- `frontend/style.css`
- `frontend/app.js`
- `database/schema.sql`
- *(Plus all Phase 2 files listed in the structure above, as they currently exist untracked in Git).*

## 8. Files Modified
- `build.gradle` (JUnit added)
- `Main.java` (wired HTTP Server and dependencies)
- `database/schema.sql` (added users table)
- `frontend/index.html` (added registration form)
- `frontend/style.css`
- `frontend/app.js` (added fetch logic)

## 9. Files Intentionally Untouched
- `.gitignore`
- `settings.gradle` (after initial creation)

## 10. Important File/Configuration Behavior
**CONTRADICTION DETECTED:**
- *History says:* `Main.java` only printed "Backend is running!" and did not act as an HTTP server. `schema.sql` had no user table. Frontend was just a placeholder.
- *Authoritative Repository State:* `Main.java` instantiates a Java `HttpServer` on port 8080 with a CORS filter. `schema.sql` contains a full `users` table blueprint. The frontend contains a full functional registration form communicating via `fetch`.
*Resolution:* The repository state dictates that the application is currently a functioning web server capable of processing user registration.

## 11. Gradle Configuration
The actual `build.gradle` uses the `java` and `application` plugins, configuring `com.webapp.Main` as the main class, setting UTF-8 encoding, and explicitly enabling JUnit Platform for testing.

## 12. Java Configuration
Java 21 is targeted and executed via Gradle.

## 13. MySQL Connector/J Configuration
```gradle
implementation 'mysql:mysql-connector-java:8.0.33'
```

## 14. Java/Gradle Versions
- Java: OpenJDK 21.0.12 (historical)
- Gradle: 8.10.2

## 15. Environment and Important Paths
Gradle wrapper does **not** exist in the repository. The successful Gradle execution used the explicit local path:
`/c/Gradle/gradle-8.10.2/bin/gradle` inside a Git Bash (MINGW64) terminal.

## 16. Chronological Implementation Journey
1. Initialized Gradle project folders manually.
2. Setup `settings.gradle` and `build.gradle`.
3. Java package mistake: `src/main/java/com/Main.java` created initially, then correctly moved to `src/main/java/com/webapp/Main.java`.
4. Frontend JavaScript filename mismatch: `index.html` referenced `script.js` but file was `app.js`. Corrected to `app.js`.
5. `build.gradle` and `Main.java` were temporarily emptied (0 bytes) during implementation and restored.
6. Gradle path issue: Required explicit path to `/c/Gradle/gradle-8.10.2/bin/gradle run`.
7. **Phase 2 (Current State):** The user actively implemented Test-Driven Development (TDD) for User Registration, including JDBC repositories, Password Hashing (PBKDF2), and HTTP Handlers.

## 17-19. Failed Attempts, Root Causes, and Fixes
- **Failed Attempt:** Running `mkdir src\main\java\com\webapp` failed due to PowerShell treating `src` as a module. **Fix:** It succeeded once executed natively as a directory creation.
- **Failed Attempt:** Gradle run failed because `build.gradle` became 0 bytes. **Fix:** Repopulated with required gradle plugins.
- **Failed Attempt:** Compile failed due to empty `Main.java` after a file move. **Fix:** Repopulated the Java class.
- **Failed Attempt:** TDD tests failed successfully in RED steps because classes were missing. **Fix:** Implemented GREEN steps correctly.
- **Failed Attempt:** `touch src/main/java/com/webapp/http/RegistrationHandler.java` failed because parent directory didn't exist. **Fix:** Ran `mkdir -p` first.

## 20. Commands That Materially Mattered
```bash
mkdir -p src/main/java/com/webapp/security
/c/Gradle/gradle-8.10.2/bin/gradle run
/c/Gradle/gradle-8.10.2/bin/gradle test
```

## 21-22. Verification Performed & Exact Results
- **Initial Verification (Phase 1):** Verified backend startup message. `Backend is running! Ready to serve the frontend.` (ACTUALLY VERIFIED)
- **Subsequent Verification (Phase 2):** Gradle test suite passed successfully. (ACTUALLY VERIFIED)

## 23. Architecture/Design Decisions
- Framework-free design enforced. 
- PBKDF2WithHmacSHA256 chosen for password hashing using `javax.crypto` to avoid external dependencies.
- Standard Java `com.sun.net.httpserver.HttpServer` chosen for backend API handling.

## 24-25. Important Limitations & Security Considerations
- Hardcoded localhost endpoints in JS.
- CORS filter manually implemented to allow browser requests to Java server.
- Plain JDBC connection managed via environment variables to prevent secret leakage.

## 26. Deferred/Out-of-Scope Work
- Login, Sessions, JWT, Password reset, OTP, Email verification are entirely deferred.

## 27. Unresolved Issues/Risks
- None currently blocking the registration flow.

## 28. Git Branch/Commit Information
- Current Branch: `main` (up to date with `origin/main`).
- Git Status: 6 modified files (`build.gradle`, `schema.sql`, `app.js`, `index.html`, `style.css`, `Main.java`).
- Untracked files: 6 untracked folders corresponding to the `http`, `model`, `repository`, `security`, `service`, and `test` directories.
- **No Phase 1 or Phase 2 changes have been committed yet.**

## 29. Current Repository State
The repository contains a fully functioning, locally testable User Registration backend and frontend, despite the historical document suggesting this was deferred.

## 30. Resume From Here
The next planned feature is **LOGIN/AUTHENTICATION**.
The environment already contains a fully implemented Registration flow (HTML -> Vanilla JS -> Java HTTP backend -> JDBC -> MySQL).
Future agents should begin by reviewing the existing `RegistrationHandler` and `JdbcUserRepository` to understand the established architectural patterns before implementing the Login endpoint, credential verification, and session management.
