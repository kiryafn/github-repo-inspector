# GitHub Repository Inspector

Simple Spring Boot application that fetches **non-fork repositories** for a given GitHub user, including their **branches** and **last commit SHA**.

---

## Tech Stack
- Java 21
- Spring 3.5
- JUnit 5
- Gradle

---


## API Endpoints

```http request
GET /api/github/{username}/repos
```

Returns a list of non-fork repositories and their branches for a given GitHub user.

Example request
```http request
GET http://localhost:8080/api/github/octocat/repos
```
✅ Example Success Response (200 OK)
```json
{
"name": "Hello-World",
"ownerLogin": "octocat",
"branches": [
  {
    "name": "main",
    "lastCommitSha": "e43a48e..."
  }
]
}
```

❌ Example Error Response (404 Not Found)
```json
{
  "status": 404,
  "message": "GitHub user not found: someInvalidUser"
}
```
---


## Project Structure
```json
src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── controller/         # REST controller
│   │   ├── client/             # GitHub API client
│   │   ├── model/dto/          # DTOs
│   │   └── exception/          # Custom exceptions and handler
│   └── resources/
│       └── application.yml     # Base URL for GitHub API
│
├── test/java/com/example/demo/integration/
│   └── GitHubApiIntegrationTest.java
```
---

## Author
Prepared as part of the recruitment process for **Atipera**
