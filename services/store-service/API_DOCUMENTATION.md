# Store Service API Documentation

## Base URL
```
http://localhost:8081/api/stores
```

## Endpoints

### Create Store
- **URL**: `/api/stores/{userId}`
- **Method**: `POST`
- **Description**: Creates a new store for the specified user
- **Path Parameters**:
  - `userId`: The ID of the user who will own the store
- **Request Body**:
```json
{
    "storeName": "string",
    "description": "string",
    "category": "string",
    "address": "string",
    "contactEmail": "string",
    "phone": "string",
    "storeLogoUrl": "string",
    "bannerUrl": "string",
    "policies": "string",
    "location": "string",
    "businessHours": "string"
}
```
- **Response**:
  - Status: 201 Created
  - Body: StoreResponse object
- **Error Cases**:
  - 409 Conflict: If user already has a store or store name is taken

### Get Store by User ID
- **URL**: `/api/stores/{userId}`
- **Method**: `GET`
- **Description**: Retrieves store information for the specified user
- **Path Parameters**:
  - `userId`: The ID of the user who owns the store
- **Response**:
  - Status: 200 OK
  - Body: StoreResponse object
- **Error Cases**:
  - 404 Not Found: If user doesn't have a store

### Check Store Existence
- **URL**: `/api/stores/check/{userId}`
- **Method**: `GET`
- **Description**: Checks if the specified user has a store
- **Path Parameters**:
  - `userId`: The ID of the user to check
- **Response**:
  - Status: 200 OK
  - Body: boolean (true if store exists, false otherwise)

### Get Store by ID (Public)
- **URL**: `/api/stores/public/{storeId}`
- **Method**: `GET`
- **Description**: Retrieves store information by store ID
- **Response**:
  - Status: 200 OK
  - Body: StoreResponse object
- **Error Cases**:
  - 404 Not Found: If store with given ID doesn't exist

### Get Store by Name (Public)
- **URL**: `/api/stores/public/name/{storeName}`
- **Method**: `GET`
- **Description**: Retrieves store information by store name
- **Response**:
  - Status: 200 OK
  - Body: StoreResponse object
- **Error Cases**:
  - 404 Not Found: If store with given name doesn't exist

## Response Object Format (StoreResponse)
```json
{
    "id": "number",
    "storeName": "string",
    "description": "string",
    "category": "string",
    "address": "string",
    "contactEmail": "string",
    "phone": "string",
    "storeLogoUrl": "string",
    "bannerUrl": "string",
    "policies": "string",
    "location": "string",
    "businessHours": "string",
    "sellerId": "number",
    "createdAt": "string (ISO-8601 datetime)",
    "updatedAt": "string (ISO-8601 datetime)",
    "active": "boolean"
}
```

## Error Response Format
```json
{
    "timestamp": "string (ISO-8601 datetime)",
    "status": "number",
    "error": "string",
    "message": "string",
    "path": "string"
}
``` 