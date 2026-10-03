# Phase 1: Project Boilerplate / Initialization

## Purpose
The goal of this phase was to create a framework-free web application foundation using HTML, CSS, Vanilla JavaScript, Java, Gradle, and MySQL.

## Starting State
- Empty project workspace.

## Requirements and Constraints
- **Framework-free architecture:** The project intentionally does NOT use Spring Boot, Spring Framework, React, Angular, Vue, Hibernate/JPA, or other application frameworks.
- Frontend must be kept in a separate top-level `frontend/` directory.
- Database scripts must be kept in `database/`.

## Technologies Selected
- Frontend: HTML + CSS + Vanilla JavaScript
- Backend: Plain Java (Java 21)
- Database: MySQL
- Build Tool: Gradle (8.10.2)

## Final Architecture
```text
Frontend (HTML + CSS + Vanilla JavaScript)
        ↓
Backend (Plain Java)
        ↓
Database (MySQL)
```

## Chronological Implementation Journey
1. Started with an empty project workspace.
2. Created the basic project directories.
3. Created `settings.gradle` and `build.gradle`.
4. Configured Gradle as a Java application.
5. Added the MySQL Connector/J dependency.
6. Configured `com.webapp.Main` as the application entry point.
7. Created `Main.java`.
8. Initially placed `Main.java` in the wrong package directory and later attempted to move it to `src/main/java/com/webapp/Main.java`. *(Note: See Current Repository State regarding this)*.
9. Created the frontend structure (`frontend/index.html`, `frontend/style.css`, `frontend/app.js`).
10. Fixed a JavaScript filename mismatch where `index.html` referenced `script.js` while the actual file was `app.js`.
11. Created `database/schema.sql`.
12. Installed/configured Gradle on Windows. The machine was found to have Java 21 installed.
13. Gradle 8.10.2 was downloaded and extracted under `C:\Gradle\gradle-8.10.2`.
14. Windows PATH configuration was attempted for Gradle.
15. Because the development terminal was Git Bash, Gradle was eventually executed using an absolute path.
16. `build.gradle` and `Main.java` were temporarily found empty during troubleshooting and were restored.
17. The final Gradle run succeeded.

## Final Directory Structure
```text
Webapp-authentication/
├── .gradle/
├── .vscode/
├── bin/
├── build/
├── build.gradle
├── database/
│   └── schema.sql
├── frontend/
│   ├── app.js
│   ├── index.html
│   └── style.css
├── gradle.zip
├── settings.gradle
└── src/
    └── main/
        └── java/
            └── com/
                ├── Main.java (Leftover from previous step)
                └── webapp/
                    └── Main.java
```

## Files Created / Modified
- `build.gradle`: Configured with `java` and `application` plugins. Set `mainClass = 'com.webapp.Main'`. Contains `mysql:mysql-connector-java:8.0.33` dependency.
- `settings.gradle`: Set `rootProject.name = 'webapp-authentication'`.
- `src/main/java/com/webapp/Main.java`: Contains a simple `main` method that prints "Backend is running! Ready to serve the frontend."
- `frontend/index.html`: Basic HTML5 boilerplate linking to `style.css` and `app.js`.
- `frontend/style.css`: Basic container centering and styling.
- `frontend/app.js`: Simple `console.log` on DOMContentLoaded.
- `database/schema.sql`: Contains commented-out instructions for a `users` table to be added later.

## Environment/Setup Details
- **Java Version:** Java 21
- **Gradle Version:** Gradle 8.10.2 (located at `C:\Gradle\gradle-8.10.2`)
- **Git:** Git is not currently initialized in this directory.

## Commands That Materially Mattered
- Executed Gradle run via Git Bash:
  `/c/Gradle/gradle-8.10.2/bin/gradle run`

## Verification Performed
- The Java application successfully executed its `Main` class.
- Console output produced: `Backend is running! Ready to serve the frontend.`

## Failed Attempts & Troubleshooting
- **File Placement:** `Main.java` was initially placed under the wrong directory relative to `package com.webapp`. It was moved, but the old file was left behind (see Unresolved Issues).
- **Filename Mismatch:** `index.html` initially referenced `script.js` instead of the created `app.js`. This was fixed.
- **Empty Files:** `build.gradle` and `Main.java` temporarily became empty during troubleshooting and were restored.
- **Environment PATH:** Gradle was initially not available through the terminal PATH. As a workaround in Git Bash, the absolute path `/c/Gradle/gradle-8.10.2/bin/gradle run` was used successfully.

## Security Considerations
- No authentication or user table was implemented during Phase 1 (deferred to Phase 2).

## Commit Information
- Git is not initialized (`fatal: not a git repository`). There are no commits or branches.

## Contradictions / Current Repository State
- **Contradiction on `Main.java` move:** The implementation history stated `Main.java` was "moved" into `src/main/java/com/webapp/Main.java`. However, inspection of the filesystem shows that `src/main/java/com/Main.java` still exists alongside the new correct file.
- **Gradle ZIP:** A large `gradle.zip` file is currently present in the root directory.

## Resume From Here
- Next Phase: Implementation of Registration Functionality.
- Need to clean up `src/main/java/com/Main.java` left over from the misplacement.
- Consider initializing a Git repository to track changes.
- Begin connecting the Java Backend to MySQL and implement the user registration endpoints.
