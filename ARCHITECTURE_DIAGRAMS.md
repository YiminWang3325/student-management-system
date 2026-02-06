# Student Management System - Visual Architecture

## System Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────────────┐
│                           CLIENT LAYER                                   │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  ┌─────────────┐ │
│  │   Browser    │  │   Postman    │  │     cURL     │  │  Mobile App │ │
│  └──────────────┘  └──────────────┘  └──────────────┘  └─────────────┘ │
└─────────────────────────────────────────────────────────────────────────┘
                                    │
                                    │ HTTP/JSON
                                    ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                      SPRING BOOT APPLICATION                             │
│                                                                           │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │                    CONTROLLER LAYER                                 │ │
│  │  ┌──────────────────────────────────────────────────────────────┐  │ │
│  │  │  StudentController.java                                       │  │ │
│  │  │  ┌────────────┐ ┌────────────┐ ┌────────────┐ ┌───────────┐ │  │ │
│  │  │  │ POST /api/ │ │ GET /api/  │ │ PUT /api/  │ │ DELETE    │ │  │ │
│  │  │  │ students   │ │ students   │ │ students   │ │ /api/...  │ │  │ │
│  │  │  └────────────┘ └────────────┘ └────────────┘ └───────────┘ │  │ │
│  │  │  @RestController | @RequestMapping | @Valid                  │  │ │
│  │  └──────────────────────────────────────────────────────────────┘  │ │
│  └────────────────────────────────────────────────────────────────────┘ │
│                                    │                                     │
│                                    ▼                                     │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │                      DTO LAYER                                      │ │
│  │  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐            │ │
│  │  │  Request DTO │  │ Response DTO │  │  Update DTO  │            │ │
│  │  │  @NotBlank   │  │   @Builder   │  │   Optional   │            │ │
│  │  │  @Email      │  │   @Data      │  │   Fields     │            │ │
│  │  └──────────────┘  └──────────────┘  └──────────────┘            │ │
│  └────────────────────────────────────────────────────────────────────┘ │
│                                    │                                     │
│                                    ▼                                     │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │                    SERVICE LAYER                                    │ │
│  │  ┌──────────────────────────────────────────────────────────────┐  │ │
│  │  │  StudentService (Interface)                                   │  │ │
│  │  │  - createStudent()  - getAllStudents()                        │  │ │
│  │  │  - getStudentById() - updateStudent()                         │  │ │
│  │  │  - deleteStudent()  - getStudentByEmail()                     │  │ │
│  │  └──────────────────────────────────────────────────────────────┘  │ │
│  │                              │                                       │ │
│  │                              ▼                                       │ │
│  │  ┌──────────────────────────────────────────────────────────────┐  │ │
│  │  │  StudentServiceImpl                                           │  │ │
│  │  │  - Business Logic                                             │  │ │
│  │  │  - Email Uniqueness Check                                     │  │ │
│  │  │  - Entity ↔ DTO Mapping                                       │  │ │
│  │  │  - @Transactional                                             │  │ │
│  │  └──────────────────────────────────────────────────────────────┘  │ │
│  └────────────────────────────────────────────────────────────────────┘ │
│                                    │                                     │
│                                    ▼                                     │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │                  REPOSITORY LAYER                                   │ │
│  │  ┌──────────────────────────────────────────────────────────────┐  │ │
│  │  │  StudentRepository extends JpaRepository                      │  │ │
│  │  │  - findByEmail(String email)                                  │  │ │
│  │  │  - existsByEmail(String email)                                │  │ │
│  │  │  - existsByEmailAndIdNot(String email, Long id)               │  │ │
│  │  │  + All JpaRepository methods (save, findAll, findById, etc.)  │  │ │
│  │  └──────────────────────────────────────────────────────────────┘  │ │
│  └────────────────────────────────────────────────────────────────────┘ │
│                                    │                                     │
│                                    ▼                                     │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │                     ENTITY LAYER                                    │ │
│  │  ┌──────────────────────────────────────────────────────────────┐  │ │
│  │  │  Student.java                                                 │  │ │
│  │  │  @Entity @Table(name = "students")                            │  │ │
│  │  │  ┌────────┬──────┬────────┬────────┬───────────┬───────────┐ │  │ │
│  │  │  │   id   │ name │  age   │ gender │   email   │timestamps │ │  │ │
│  │  │  │  Long  │String│Integer │ String │  String   │LocalDate  │ │  │ │
│  │  │  │  @Id   │@Size │ @Min   │@Pattern│  @Email   │@Creation  │ │  │ │
│  │  │  │ @Gen   │2-100 │ @Max   │ MALE/  │  unique   │@Update    │ │  │ │
│  │  │  │        │      │ 1-150  │FEMALE/ │           │Timestamp  │ │  │ │
│  │  │  │        │      │        │ OTHER  │           │           │ │  │ │
│  │  │  └────────┴──────┴────────┴────────┴───────────┴───────────┘ │  │ │
│  │  └──────────────────────────────────────────────────────────────┘  │ │
│  └────────────────────────────────────────────────────────────────────┘ │
│                                    │                                     │
│                                    ▼                                     │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │                  EXCEPTION HANDLING                                 │ │
│  │  ┌──────────────────────────────────────────────────────────────┐  │ │
│  │  │  GlobalExceptionHandler (@RestControllerAdvice)              │  │ │
│  │  │  ┌────────────────┬────────────────┬────────────────────┐    │  │ │
│  │  │  │ 400 Bad Request│ 404 Not Found  │ 409 Conflict       │    │  │ │
│  │  │  │ Validation     │ Resource Not   │ Duplicate Email    │    │  │ │
│  │  │  │ Errors         │ Found          │                    │    │  │ │
│  │  │  └────────────────┴────────────────┴────────────────────┘    │  │ │
│  │  └──────────────────────────────────────────────────────────────┘  │ │
│  └────────────────────────────────────────────────────────────────────┘ │
│                                                                           │
└─────────────────────────────────────────────────────────────────────────┘
                                    │
                                    │ JDBC
                                    ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                         DATABASE LAYER                                   │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │  MySQL Database (Production)                                       │ │
│  │  ┌──────────────────────────────────────────────────────────────┐ │ │
│  │  │  Database: student_db                                         │ │ │
│  │  │  Table: students                                              │ │ │
│  │  │  - Auto DDL with Hibernate                                    │ │ │
│  │  │  - Connection Pool: HikariCP                                  │ │ │
│  │  └──────────────────────────────────────────────────────────────┘ │ │
│  └────────────────────────────────────────────────────────────────────┘ │
│                                                                           │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │  H2 Database (Testing)                                             │ │
│  │  - In-memory database                                              │ │
│  │  - Auto-created for tests                                          │ │
│  │  - Isolated test environment                                       │ │
│  └────────────────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────────────┘
```

## Request Flow Diagram

```
┌──────────┐
│  Client  │
└────┬─────┘
     │
     │ 1. HTTP Request (POST /api/students)
     │    Body: {"name":"John","age":20,"gender":"MALE","email":"john@example.com"}
     ▼
┌─────────────────────┐
│ StudentController   │
│ @RestController     │
└────┬────────────────┘
     │
     │ 2. Validate Request (@Valid)
     │    - Check @NotBlank, @Email, @Min, @Max
     │
     ▼
┌─────────────────────┐
│ StudentServiceImpl  │
│ @Service            │
└────┬────────────────┘
     │
     │ 3. Business Logic
     │    - Check email uniqueness
     │    - Map DTO to Entity
     │
     ▼
┌─────────────────────┐
│ StudentRepository   │
│ JpaRepository       │
└────┬────────────────┘
     │
     │ 4. Database Operation
     │    - INSERT INTO students...
     │
     ▼
┌─────────────────────┐
│  MySQL Database     │
└────┬────────────────┘
     │
     │ 5. Return Saved Entity
     │
     ▼
┌─────────────────────┐
│ StudentServiceImpl  │
└────┬────────────────┘
     │
     │ 6. Map Entity to Response DTO
     │
     ▼
┌─────────────────────┐
│ StudentController   │
└────┬────────────────┘
     │
     │ 7. Wrap in ApiResponse
     │    - success: true
     │    - message: "Student created successfully"
     │    - data: StudentResponseDTO
     │    - timestamp: LocalDateTime.now()
     │
     ▼
┌──────────┐
│  Client  │ 201 Created
└──────────┘ {"success":true,"message":"...","data":{...}}
```

## Error Handling Flow

```
┌──────────┐
│  Client  │
└────┬─────┘
     │
     │ HTTP Request with Invalid Data
     │ Body: {"name":"A","age":200,"email":"invalid"}
     ▼
┌─────────────────────┐
│ StudentController   │
└────┬────────────────┘
     │
     │ @Valid Validation Fails
     │ MethodArgumentNotValidException thrown
     │
     ▼
┌──────────────────────────┐
│ GlobalExceptionHandler   │
│ @RestControllerAdvice    │
└────┬─────────────────────┘
     │
     │ Catch Exception
     │ Extract Validation Errors
     │
     ▼
┌─────────────────────┐
│ Build Error Response│
│ - success: false    │
│ - message: "..."    │
│ - data: {errors}    │
└────┬────────────────┘
     │
     │ 400 Bad Request
     ▼
┌──────────┐
│  Client  │
└──────────┘
{
  "success": false,
  "message": "Validation failed",
  "data": {
    "name": "Name must be between 2 and 100 characters",
    "age": "Age must not exceed 150",
    "email": "Email must be valid"
  }
}
```

## Database Schema

```
┌─────────────────────────────────────────────────────────────┐
│                    students TABLE                            │
├──────────────┬──────────────┬─────────────┬─────────────────┤
│ Column       │ Type         │ Constraints │ Description     │
├──────────────┼──────────────┼─────────────┼─────────────────┤
│ id           │ BIGINT       │ PRIMARY KEY │ Auto-generated  │
│              │              │ AUTO_INC    │ student ID      │
├──────────────┼──────────────┼─────────────┼─────────────────┤
│ name         │ VARCHAR(100) │ NOT NULL    │ Student name    │
│              │              │ CHECK(2-100)│                 │
├──────────────┼──────────────┼─────────────┼─────────────────┤
│ age          │ INTEGER      │ NOT NULL    │ Student age     │
│              │              │ CHECK(1-150)│                 │
├──────────────┼──────────────┼─────────────┼─────────────────┤
│ gender       │ VARCHAR(10)  │ NOT NULL    │ MALE/FEMALE/    │
│              │              │             │ OTHER           │
├──────────────┼──────────────┼─────────────┼─────────────────┤
│ email        │ VARCHAR(255) │ NOT NULL    │ Unique email    │
│              │              │ UNIQUE      │ address         │
├──────────────┼──────────────┼─────────────┼─────────────────┤
│ created_at   │ TIMESTAMP    │ NOT NULL    │ Creation time   │
│              │              │ DEFAULT NOW │ (auto)          │
├──────────────┼──────────────┼─────────────┼─────────────────┤
│ updated_at   │ TIMESTAMP    │             │ Last update     │
│              │              │ ON UPDATE   │ time (auto)     │
└──────────────┴──────────────┴─────────────┴─────────────────┘

Indexes:
  - PRIMARY KEY (id)
  - UNIQUE INDEX (email)
```

## Testing Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    TEST LAYER                                │
├─────────────────────────────────────────────────────────────┤
│                                                               │
│  ┌────────────────────────────────────────────────────────┐ │
│  │  Integration Tests (Controller Layer)                  │ │
│  │  ┌──────────────────────────────────────────────────┐  │ │
│  │  │  StudentControllerTest.java                      │  │ │
│  │  │  - @WebMvcTest                                   │  │ │
│  │  │  - MockMvc for HTTP testing                      │  │ │
│  │  │  - @MockBean for service layer                   │  │ │
│  │  │  - Tests: 7                                      │  │ │
│  │  └──────────────────────────────────────────────────┘  │ │
│  └────────────────────────────────────────────────────────┘ │
│                                                               │
│  ┌────────────────────────────────────────────────────────┐ │
│  │  Unit Tests (Service Layer)                            │ │
│  │  ┌──────────────────────────────────────────────────┐  │ │
│  │  │  StudentServiceTest.java                         │  │ │
│  │  │  - @ExtendWith(MockitoExtension.class)           │  │ │
│  │  │  - @Mock for repository                          │  │ │
│  │  │  - @InjectMocks for service                      │  │ │
│  │  │  - Tests: 11                                     │  │ │
│  │  └──────────────────────────────────────────────────┘  │ │
│  └────────────────────────────────────────────────────────┘ │
│                                                               │
│  ┌────────────────────────────────────────────────────────┐ │
│  │  Context Tests                                          │ │
│  │  ┌──────────────────────────────────────────────────┐  │ │
│  │  │  StudentManagementApplicationTests.java          │  │ │
│  │  │  - @SpringBootTest                               │  │ │
│  │  │  - Context load verification                     │  │ │
│  │  │  - Tests: 1                                      │  │ │
│  │  └──────────────────────────────────────────────────┘  │ │
│  └────────────────────────────────────────────────────────┘ │
│                                                               │
│  ┌────────────────────────────────────────────────────────┐ │
│  │  Test Database (H2)                                     │ │
│  │  - In-memory database                                   │ │
│  │  - Separate application.properties                      │ │
│  │  - Auto-created and destroyed                           │ │
│  └────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────┘
```

## Deployment Options

```
┌─────────────────────────────────────────────────────────────┐
│                  DEPLOYMENT OPTIONS                          │
├─────────────────────────────────────────────────────────────┤
│                                                               │
│  Option 1: Local Development                                 │
│  ┌────────────────────────────────────────────────────────┐ │
│  │  mvn spring-boot:run                                   │ │
│  │  - Runs on localhost:8080                              │ │
│  │  - Hot reload with spring-boot-devtools (optional)     │ │
│  └────────────────────────────────────────────────────────┘ │
│                                                               │
│  Option 2: Standalone JAR                                    │
│  ┌────────────────────────────────────────────────────────┐ │
│  │  java -jar student-management-system-1.0.0.jar         │ │
│  │  - Executable JAR with embedded Tomcat                 │ │
│  │  - No external server required                         │ │
│  └────────────────────────────────────────────────────────┘ │
│                                                               │
│  Option 3: Docker Container (Future)                         │
│  ┌────────────────────────────────────────────────────────┐ │
│  │  docker build -t student-management-system .           │ │
│  │  docker run -p 8080:8080 student-management-system     │ │
│  └────────────────────────────────────────────────────────┘ │
│                                                               │
│  Option 4: Cloud Deployment (Future)                         │
│  ┌────────────────────────────────────────────────────────┐ │
│  │  - AWS Elastic Beanstalk                               │ │
│  │  - Heroku                                              │ │
│  │  - Azure App Service                                   │ │
│  │  - Google Cloud Run                                    │ │
│  └────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────┘
```

## Technology Stack Visualization

```
┌─────────────────────────────────────────────────────────────┐
│                   TECHNOLOGY STACK                           │
├─────────────────────────────────────────────────────────────┤
│                                                               │
│  Frontend (Future)                                           │
│  ┌────────────────────────────────────────────────────────┐ │
│  │  React / Angular / Vue.js                              │ │
│  └────────────────────────────────────────────────────────┘ │
│                          │                                   │
│                          │ REST API (JSON)                   │
│                          ▼                                   │
│  Backend (Current)                                           │
│  ┌────────────────────────────────────────────────────────┐ │
│  │  Spring Boot 3.2.2                                     │ │
│  │  ├─ Spring Web (REST API)                              │ │
│  │  ├─ Spring Data JPA (ORM)                              │ │
│  │  ├─ Spring Validation (Bean Validation)                │ │
│  │  └─ Lombok (Code Generation)                           │ │
│  └────────────────────────────────────────────────────────┘ │
│                          │                                   │
│                          │ JDBC                              │
│                          ▼                                   │
│  Database                                                    │
│  ┌────────────────────────────────────────────────────────┐ │
│  │  MySQL 8.0+ (Production)                               │ │
│  │  H2 Database (Testing)                                 │ │
│  └────────────────────────────────────────────────────────┘ │
│                                                               │
│  Build & Test                                                │
│  ┌────────────────────────────────────────────────────────┐ │
│  │  Maven 3.9+ (Build Tool)                               │ │
│  │  JUnit 5 (Testing Framework)                           │ │
│  │  Mockito (Mocking Framework)                           │ │
│  └────────────────────────────────────────────────────────┘ │
│                                                               │
│  Runtime                                                     │
│  ┌────────────────────────────────────────────────────────┐ │
│  │  Java 17+ (JDK)                                        │ │
│  │  Embedded Tomcat (Web Server)                          │ │
│  │  HikariCP (Connection Pool)                            │ │
│  └────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────┘
```
