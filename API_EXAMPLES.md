# API Testing Examples - Student Management System

## Base URL
```
http://localhost:8080/api/students
```

## 1. Create Student (POST)

### Request
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

### Windows (PowerShell)
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/students" `
  -Method POST `
  -ContentType "application/json" `
  -Body '{"name":"John Doe","age":20,"gender":"MALE","email":"john.doe@example.com"}'
```

### Expected Response (201 Created)
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
    "createdAt": "2026-01-29T21:30:00",
    "updatedAt": "2026-01-29T21:30:00"
  },
  "timestamp": "2026-01-29T21:30:00"
}
```

---

## 2. Get All Students (GET)

### Request
```bash
curl http://localhost:8080/api/students
```

### Windows (PowerShell)
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/students" -Method GET
```

### Expected Response (200 OK)
```json
{
  "success": true,
  "message": "Students retrieved successfully",
  "data": [
    {
      "id": 1,
      "name": "John Doe",
      "age": 20,
      "gender": "MALE",
      "email": "john.doe@example.com",
      "createdAt": "2026-01-29T21:30:00",
      "updatedAt": "2026-01-29T21:30:00"
    }
  ],
  "timestamp": "2026-01-29T21:30:00"
}
```

---

## 3. Get Student by ID (GET)

### Request
```bash
curl http://localhost:8080/api/students/1
```

### Windows (PowerShell)
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/students/1" -Method GET
```

### Expected Response (200 OK)
```json
{
  "success": true,
  "message": "Student retrieved successfully",
  "data": {
    "id": 1,
    "name": "John Doe",
    "age": 20,
    "gender": "MALE",
    "email": "john.doe@example.com",
    "createdAt": "2026-01-29T21:30:00",
    "updatedAt": "2026-01-29T21:30:00"
  },
  "timestamp": "2026-01-29T21:30:00"
}
```

---

## 4. Update Student (PUT)

### Request
```bash
curl -X PUT http://localhost:8080/api/students/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Updated",
    "age": 21,
    "email": "john.updated@example.com"
  }'
```

### Windows (PowerShell)
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/students/1" `
  -Method PUT `
  -ContentType "application/json" `
  -Body '{"name":"John Updated","age":21,"email":"john.updated@example.com"}'
```

### Partial Update (Only Name)
```bash
curl -X PUT http://localhost:8080/api/students/1 \
  -H "Content-Type: application/json" \
  -d '{"name": "John Smith"}'
```

### Expected Response (200 OK)
```json
{
  "success": true,
  "message": "Student updated successfully",
  "data": {
    "id": 1,
    "name": "John Updated",
    "age": 21,
    "gender": "MALE",
    "email": "john.updated@example.com",
    "createdAt": "2026-01-29T21:30:00",
    "updatedAt": "2026-01-29T21:35:00"
  },
  "timestamp": "2026-01-29T21:35:00"
}
```

---

## 5. Delete Student (DELETE)

### Request
```bash
curl -X DELETE http://localhost:8080/api/students/1
```

### Windows (PowerShell)
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/students/1" -Method DELETE
```

### Expected Response (200 OK)
```json
{
  "success": true,
  "message": "Student deleted successfully",
  "data": null,
  "timestamp": "2026-01-29T21:40:00"
}
```

---

## 6. Find Student by Email (GET)

### Request
```bash
curl http://localhost:8080/api/students/email/john.doe@example.com
```

### Windows (PowerShell)
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/students/email/john.doe@example.com" -Method GET
```

### Expected Response (200 OK)
```json
{
  "success": true,
  "message": "Student retrieved successfully",
  "data": {
    "id": 1,
    "name": "John Doe",
    "age": 20,
    "gender": "MALE",
    "email": "john.doe@example.com",
    "createdAt": "2026-01-29T21:30:00",
    "updatedAt": "2026-01-29T21:30:00"
  },
  "timestamp": "2026-01-29T21:30:00"
}
```

---

## Error Responses

### 400 Bad Request - Validation Error
```json
{
  "success": false,
  "message": "Validation failed",
  "data": {
    "name": "Name must be between 2 and 100 characters",
    "age": "Age must be at least 1",
    "email": "Email must be valid"
  },
  "timestamp": "2026-01-29T21:30:00"
}
```

### 404 Not Found
```json
{
  "success": false,
  "message": "Student not found with id: 999",
  "data": null,
  "timestamp": "2026-01-29T21:30:00"
}
```

### 409 Conflict - Duplicate Email
```json
{
  "success": false,
  "message": "Student with email john.doe@example.com already exists",
  "data": null,
  "timestamp": "2026-01-29T21:30:00"
}
```

---

## Test Data Examples

### Valid Students
```json
{
  "name": "Alice Johnson",
  "age": 22,
  "gender": "FEMALE",
  "email": "alice.johnson@example.com"
}
```

```json
{
  "name": "Bob Smith",
  "age": 19,
  "gender": "MALE",
  "email": "bob.smith@example.com"
}
```

```json
{
  "name": "Charlie Brown",
  "age": 25,
  "gender": "OTHER",
  "email": "charlie.brown@example.com"
}
```

### Invalid Examples (Will Return 400)

**Invalid Email:**
```json
{
  "name": "Test User",
  "age": 20,
  "gender": "MALE",
  "email": "invalid-email"
}
```

**Age Out of Range:**
```json
{
  "name": "Test User",
  "age": 200,
  "gender": "MALE",
  "email": "test@example.com"
}
```

**Invalid Gender:**
```json
{
  "name": "Test User",
  "age": 20,
  "gender": "UNKNOWN",
  "email": "test@example.com"
}
```

**Name Too Short:**
```json
{
  "name": "A",
  "age": 20,
  "gender": "MALE",
  "email": "test@example.com"
}
```

---

## Postman Collection

You can import these endpoints into Postman:

1. Create a new collection: "Student Management API"
2. Add requests for each endpoint above
3. Set the base URL as a collection variable: `{{baseUrl}}` = `http://localhost:8080`
4. Use the JSON examples above as request bodies

---

## Testing Workflow

### Complete CRUD Test Sequence
```bash
# 1. Create a student
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{"name":"Test Student","age":20,"gender":"MALE","email":"test@example.com"}'

# 2. Get all students
curl http://localhost:8080/api/students

# 3. Get student by ID (use ID from step 1)
curl http://localhost:8080/api/students/1

# 4. Update the student
curl -X PUT http://localhost:8080/api/students/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Updated Student","age":21}'

# 5. Find by email
curl http://localhost:8080/api/students/email/test@example.com

# 6. Delete the student
curl -X DELETE http://localhost:8080/api/students/1

# 7. Verify deletion (should return 404)
curl http://localhost:8080/api/students/1
```

---

## Notes

- All timestamps are in ISO-8601 format
- Email addresses must be unique across all students
- Gender must be one of: MALE, FEMALE, OTHER
- Age must be between 1 and 150
- Name must be between 2 and 100 characters
- All fields are required for POST requests
- All fields are optional for PUT requests (partial updates supported)
