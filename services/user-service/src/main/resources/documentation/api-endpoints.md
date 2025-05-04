# User Service API Documentation

This document provides information about the available endpoints in the User Service.

## Authentication Endpoints

### Register User
- **URL**: `/api/auth/register`
- **Method**: `POST`
- **Description**: Registers a new user in the system
- **Request Body**:
  ```json
  {
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "password": "Password123!",
    "phoneNumber": "+1234567890",
    "seller": false
  }
  ```
- **Required Fields**:
  - `firstName`: User's first name
  - `lastName`: User's last name
  - `email`: Valid email address (must be unique)
  - `password`: Password (minimum 8 characters, at least one uppercase letter and one special character)
  - `phoneNumber`: Valid phone number (10-15 digits, can include a + prefix)
- **Optional Fields**:
  - `seller`: Boolean indicating if the user wants seller privileges (default: false)
- **Success Response**: `200 OK`
  ```json
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phoneNumber": "+1234567890",
    "roles": ["CUSTOMER"],
    "emailVerified": false
  }
  ```
- **Error Responses**:
  - `400 Bad Request`: If validation fails or email already exists
    ```json
    {
      "firstName": "First name is required",
      "email": "Email already exists"
    }
    ```

### Login
- **URL**: `/api/auth/login`
- **Method**: `POST`
- **Description**: Authenticates a user and provides a JWT token
- **Request Body**:
  ```json
  {
    "email": "john.doe@example.com",
    "password": "Password123!"
  }
  ```
- **Required Fields**:
  - `email`: Registered email address
  - `password`: User's password
- **Success Response**: `200 OK`
  ```json
  {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI...",
    "userId": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "roles": ["CUSTOMER"]
  }
  ```
- **Error Responses**:
  - `401 Unauthorized`: If credentials are invalid
    ```json
    {
      "error": "Invalid email or password"
    }
    ```

## User Endpoints

### Get User by ID
- **URL**: `/api/users/{id}`
- **Method**: `GET`
- **Description**: Retrieves user information by user ID
- **Path Parameters**:
  - `id`: User ID
- **Authentication**: Required (JWT token in Authorization header)
- **Authorization**: User must have CUSTOMER role
- **Success Response**: `200 OK`
  ```json
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phoneNumber": "+1234567890",
    "roles": ["CUSTOMER"],
    "emailVerified": false
  }
  ```
- **Error Responses**:
  - `401 Unauthorized`: If not authenticated
  - `403 Forbidden`: If lacking required role
  - `404 Not Found`: If user with the provided ID doesn't exist
    ```json
    {
      "error": "User not found"
    }
    ```

### Verify Email
- **URL**: `/api/users/verify/{id}`
- **Method**: `POST`
- **Description**: Verifies a user's email address
- **Path Parameters**:
  - `id`: User ID
- **Authentication**: Required (JWT token in Authorization header)
- **Authorization**: User must have CUSTOMER role
- **Success Response**: `200 OK`
  ```json
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phoneNumber": "+1234567890",
    "roles": ["CUSTOMER"],
    "emailVerified": true
  }
  ```
- **Error Responses**:
  - `401 Unauthorized`: If not authenticated
  - `403 Forbidden`: If lacking required role
  - `404 Not Found`: If user with the provided ID doesn't exist
    ```json
    {
      "error": "User not found"
    }
    ```

