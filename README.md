# Student Management System

A production-ready RESTful API built with Spring Boot for managing student records with full CRUD operations, MySQL database integration, and comprehensive testing.

## Features

- **Complete CRUD Operations** - Create, Read, Update, and Delete student records
- **RESTful API Design** - 6 well-structured endpoints following REST principles
- **Data Validation** - Bean validation with detailed error messages
- **Exception Handling** - Global exception handler with proper HTTP status codes
- **Database Integration** - MySQL for production, H2 for testing
- **Layered Architecture** - Clean separation: Controller → Service → Repository
- **Comprehensive Testing** - 19 unit and integration tests (100% passing)
- **Docker Support** - Ready-to-deploy with Docker Compose
- **API Response Wrapper** - Standardized response format across all endpoints

## Tech Stack

- **Java** 17
- **Spring Boot** 3.4.2
- **Spring Data JPA** - Database access layer
- **MySQL** 8.0 - Production database
- **H2** - In-memory test database
- **Lombok** - Reduce boilerplate code
- **Maven** - Build and dependency management
- **JUnit 5 & Mockito** - Testing framework
- **Docker** - Containerization

## Quick Start

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+ (or use Docker)

### Option 1: Run with Docker (Recommended)

```bash
# Clone the repository
git clone <repository-url>
cd student-management-system

# Start the application with Docker Compose
docker-compose up -d

# The API will be available at http://localhost:8080
```

### Option 2: Run Locally

1. **Create MySQL Database**
   ```bash
   mysql -u root -p < database-setup.sql
   ```

2. **Configure Database**

   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=root
   spring.datasource.password=your_password
   ```

3. **Build and Run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

   Or use the startup scripts:
   - Windows: `start.bat`
   - Linux/Mac: `./start.sh`

4. **Verify**
   ```bash
   curl http://localhost:8080/api/students
   ```

## API Endpoints

| Method | Endpoint | Description | Status Code |
|--------|----------|-------------|-------------|
| POST | `/api/students` | Create a new student | 201 |
| GET | `/api/students` | Get all students | 200 |
| GET | `/api/students/{id}` | Get student by ID | 200 |
| PUT | `/api/students/{id}` | Update student | 200 |
| DELETE | `/api/students/{id}` | Delete student | 200 |
| GET | `/api/students/email/{email}` | Find by email | 200 |

## Usage Examples

### Create a Student

```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "age": 20,
    "gender": "MALE",
    "email": "john.doe@example.com"
  }'
```

**Response:**
```json
{
  "success": true,
  "message": "Student created successfully",
  "data": {
    "id": 1,
    "name": "John Doe",
    "age": 20,
    "gender": "MALE",
    "email": "john.doe@example.com",
    "createdAt": "2026-02-05T10:30:00",
    "updatedAt": "2026-02-05T10:30:00"
  },
  "timestamp": "2026-02-05T10:30:00"
}
```

### Get All Students

```bash
curl http://localhost:8080/api/students
```

### Update a Student

```bash
curl -X PUT http://localhost:8080/api/students/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Updated",
    "age": 21
  }'
```

### Delete a Student

```bash
curl -X DELETE http://localhost:8080/api/students/1
```

For more examples, see [API_EXAMPLES.md](API_EXAMPLES.md)

## Validation Rules

| Field | Rules |
|-------|-------|
| **name** | Required, 2-100 characters |
| **age** | Required, 1-150 |
| **gender** | Required, must be MALE, FEMALE, or OTHER |
| **email** | Required, valid email format, unique |

## Error Responses

The API returns standardized error responses:

```json
{
  "success": false,
  "message": "Student not found with id: 999",
  "data": null,
  "timestamp": "2026-02-05T10:30:00"
}
```

**HTTP Status Codes:**
- `400` - Bad Request (validation errors)
- `404` - Not Found (resource doesn't exist)
- `409` - Conflict (duplicate email)
- `500` - Internal Server Error

## Project Structure

```
student-management-system/
├── src/
│   ├── main/
│   │   ├── java/com/example/studentmanagement/
│   │   │   ├── controller/          # REST controllers
│   │   │   ├── service/             # Business logic
│   │   │   ├── repository/          # Data access layer
│   │   │   ├── entity/              # JPA entities
│   │   │   ├── dto/                 # Data transfer objects
│   │   │   ├── response/            # API response wrapper
│   │   │   ├── exception/           # Custom exceptions
│   │   │   └── StudentManagementApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       ├── java/                    # Test classes
│       └── resources/
│           └── application.properties
├── docker-compose.yml               # Docker configuration
├── Dockerfile                       # Docker image definition
├── pom.xml                          # Maven dependencies
├── database-setup.sql               # Database setup script
├── start.bat                        # Windows startup script
├── start.sh                         # Linux/Mac startup script
└── README.md
```

## Testing

Run all tests:
```bash
mvn test
```

**Test Results:**
- 19 tests total
- 100% passing
- Coverage: Controller, Service, and Integration tests

## Configuration

### Application Properties

**Production (MySQL):**
```properties
server.port=8080
spring.datasource.url=jdbc:mysql://localhost:3306/student_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

**Test (H2):**
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=create-drop
```

### Docker Configuration

The `docker-compose.yml` includes:
- MySQL 8.0 container (port 3307)
- Application container (port 8080)
- Persistent volume for database

## Documentation

- [SETUP_GUIDE.md](SETUP_GUIDE.md) - Detailed setup instructions
- [API_EXAMPLES.md](API_EXAMPLES.md) - Complete API testing examples
- [PROJECT_SUMMARY.md](PROJECT_SUMMARY.md) - Implementation details
- [ARCHITECTURE_DIAGRAMS.md](ARCHITECTURE_DIAGRAMS.md) - System architecture

## Development

### Build the Project

```bash
mvn clean install
```

### Run in Development Mode

```bash
mvn spring-boot:run
```

### Package as JAR

```bash
mvn clean package
java -jar target/student-management-system-1.0.0.jar
```

## Troubleshooting

### MySQL Connection Error
- Verify MySQL is running: `mysql -u root -p`
- Check credentials in `application.properties`
- Ensure database `student_db` exists

### Port 8080 Already in Use
Change the port in `application.properties`:
```properties
server.port=8081
```

### Lombok Not Working
Install Lombok plugin for your IDE:
- **IntelliJ IDEA**: Settings → Plugins → Search "Lombok"
- **Eclipse**: Download lombok.jar and run it

## Architecture Highlights

### Layered Architecture
```
Client → Controller → Service → Repository → Database
```

### Key Design Patterns
- **DTO Pattern** - Separate API contracts from entities
- **Repository Pattern** - Abstract data access
- **Service Layer** - Encapsulate business logic
- **Global Exception Handling** - Centralized error management

### Features
- Automatic timestamp management (createdAt, updatedAt)
- Email uniqueness validation
- Partial update support
- Transaction management
- Connection pooling with HikariCP

## Future Enhancements

- [ ] Pagination and sorting
- [ ] Search and filtering
- [ ] Authentication/Authorization (Spring Security)
- [ ] API documentation (Swagger/OpenAPI)
- [ ] Caching (Redis)
- [ ] Logging (SLF4J/Logback)
- [ ] Metrics and monitoring
- [ ] CI/CD pipeline
- [ ] Frontend UI

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License.

## Contact

For questions or support, please open an issue in the repository.

---

**Status:** Production Ready ✓

Built with Spring Boot 3.4.2 | Java 17 | MySQL 8.0
