# Dauletkan Nursultan Student API (IT2-2313, ID 37037)

Spring Boot + PostgreSQL CRUD REST API with documentation, built for the DevOps midterm of Dauletkan Nursultan.

| | |
|---|---|
| Student | Dauletkan Nursultan |
| Group | IT2-2313 |
| Student ID | 37037 |
| Image | `dauletkan-nursultan-student-api:v1.1-37037` |
| URL | http://localhost:8037 |

## Stack
Java 17, Spring Boot 3, Gradle wrapper, PostgreSQL 16, Docker Compose, Jenkins.

## Run
```bash
cp .env.example .env    # set your own password
docker compose up -d --build
curl http://localhost:8037/
```

## API
| Method | Path | Description |
|---|---|---|
| GET | `/` | Student/application info |
| GET | `/api/students` | List students |
| GET | `/api/students/{id}` | Get one student (404 if missing) |
| POST | `/api/students` | Create student |
| PUT | `/api/students/{id}` | Update student (404 if missing) |
| DELETE | `/api/students/{id}` | Delete student |

## Changes by Dauletkan Nursultan
- **feature/dauletkan-nursultan-crud**: implemented the *Update* operation
  (`PUT /api/students/{id}`) with validation, 404 handling and two tests.

## CI/CD
Jenkins job `Dauletkan_Nursultan_Student_API` polls this repository every 2 minutes
(`pollSCM('H/2 * * * *')`) and runs Checkout -> Build -> Test -> Docker Build -> Verify.
