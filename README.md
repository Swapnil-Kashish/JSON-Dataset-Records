# JSON-Dataset-Records

## 📌 Project Overview

**JSON-Dataset-Records** is a Spring Boot application designed to store, query, and manipulate JSON datasets.  
It provides RESTful APIs to:  
- Insert records  
- Group data  
- Sort records dynamically  

---

## 🚀 How to Start the Project

### 📌 Prerequisites
- Java 17+  
- Maven  
- PostgreSQL (or H2 for testing)  

### 🔧 Steps to Run

```sh
1. Clone the repository  
   git clone https://github.com/your-username/JSON-Dataset-Records.git  
   cd JSON-Dataset-Records  
```
2. Configure the database in `application.properties`  
```sh
   spring.datasource.url=jdbc:postgresql://localhost:5432/json_db  
   spring.datasource.username=your_username  
   spring.datasource.password=your_password  
```
3. Run the application
```sh
   mvn spring-boot:run  
```
5. The server starts on
```sh
   http://localhost:9096
```

# 📌 API Documentation

1️⃣ Insert a Record  
   - Method: POST  
   - URL: http://localhost:9096/api/dataset/employee_dataset/record  
   - Description: Inserts a new JSON record into the dataset.  
   - Request Body (JSON):
```sh
     {
       "id": 1,
       "name": "John Doe",
       "department": "Engineering",
       "age": 30
     }
```
Response (JSON):
```sh
    {
      "recordId":1,
      "message":"Record added successfully",
      "dataset":"employee_dataset"
    }
```
2️⃣ Group Records by Department  
   - Method: GET  
   - URL: http://localhost:9096/api/dataset/employee_dataset/query?groupBy=department  
   - Description: Groups the records by department.
   - Response (JSON):
```sh  
{
  "groupedRecords": {
    "Engineering": [
      {
        "id": 1,
        "age": 30,
        "name": "John Doe",
        "department": "Engineering"
      },
      {
        "id": 2,
        "age": 25,
        "name": "Jane Smith",
        "department": "Engineering"
      }
    ],
    "Marketing": [
      {
        "id": 3,
        "age": 28,
        "name": "Alice Brown",
        "department": "Marketing"
      }
    ]
  }
}
```
   

3️⃣ Sort Records by Name (Ascending Order)  
   - Method: GET  
   - URL: http://localhost:9096/api/dataset/employee_dataset/query?sortBy=name&order=asc  
   - Description: Retrieves records sorted by name in ascending order.
   - Response (JSON):
```sh
{
  "sortedRecords": [
    {
      "id": 3,
      "age": 28,
      "name": "Alice Brown",
      "department": "Marketing"
    },
    {
      "id": 2,
      "age": 25,
      "name": "Jane Smith",
      "department": "Engineering"
    },
    {
      "id": 1,
      "age": 30,
      "name": "John Doe",
      "department": "Engineering"
    }
  ]
}
```
## Access the Application:
- Once the application is running, test it through postman at http://localhost:9096/API_END_POINTS_URL (or the appropriate port if configured differently).
- Modify `application.properties` database configurations as needed.

## Contributing:
We welcome contributions from the community to further improve and enhance the Application. If you'd like to contribute, please fork the repository, make your changes, and submit a pull request.
