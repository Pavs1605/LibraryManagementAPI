#Library Management App
This is a simple Spring Boot application for Library Management  that manages books and patrons. The application also manages borrowing and returning of books and maintaining a transaction history

## TECHSTACK
* Java Spring boot
* REST API's
* H2 Database
* Apache Tomcat Server
* Swagger API

## Entities
Book
Represents a book in the library.

## Patron
Represents an patron/user who borrows/returns book in the library.

## Borrowing Records
Represents an record when a patron who borrows/returns book in the library.

## API Endpoints

### Book
![image](https://github.com/Pavs1605/SpringEmpDeptDemo/assets/18229871/785b153a-1c44-4d21-b78e-ac38acbe2206)

### Patron
![image](https://github.com/Pavs1605/SpringEmpDeptDemo/assets/18229871/b50ee32c-c498-4a5f-9d04-dcb8b19670f1)

### Borrowing Record
![image](https://github.com/Pavs1605/SpringEmpDeptDemo/assets/18229871/eb5ea195-194e-49d0-b1eb-a20e02411391)

### Example URLs
Retrieve all employees in a specific department: http://localhost:8080/departments/dept01/employees

Add an employee to a department: http://localhost:8080/departments/addEmployeeToDepartment/dept01/emp003

Delete an employee from a department: http://localhost:8080/departments/deleteEmployeeFromDepartment/dept02/emp002
