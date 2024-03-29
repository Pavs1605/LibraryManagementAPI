## Library Management App
This is a simple Spring Boot application for Library Management  that manages books and patrons. The application also manages borrowing and returning of books and maintaining a transaction history

## Design considerations
* One patron can borrow multiple books
* Same book cannot be borrowed by two people at the same time.
* System should be able to give list of free books available at any point of time.
* System should have a history of transactions for a book and a patron.
* CRUD API's for each entity should be available.

## TECHSTACK
* Java Spring boot
* REST API's
* H2 Database
* Apache Tomcat Server
* Swagger API
* Mockito Tests
* Transactional

## Postman Collection with list of API's
* Start the application by running Library Application.java class and run below end points to see data.
  ![img.png](img.png)
  Import following collection to see more results - [Postman_collection](Postman_collection)

## Entities
Book
Represents a book in the library.

## Patron
Represents an patron/user who borrows/returns book in the library.

## Borrowing Records
Represents an record when a patron who borrows/returns book in the library.

## Database entities
| Table Name          | Columns                                                                                                    | Constraints                                                                                                                             |
|---------------------|------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------|
| Borrowing Records  | borrowing_id (PK) book_id (FK: books.book_id) patron_id (FK: patrons.patron_id),  borrowing_date, return_date, book_state | Unique constraint on (book_id, patron_id, borrowing_date)                                                                             |
| Books               | book_id (PK), title (Unique), author  (Unique), publication_year, isbn_number, serial_number (Unique), description | Unique constraint on title, author, Unique constraint on serial_number                                                                          |
| Patrons             | patron_id (PK), name, contact_no, email_address (Unique) address                                           | Unique constraint on email_address                                                                                                    |


## API Endpoints
Link - https://pavs1605.github.io/LibraryManagementAPI/
### Book
![image](https://github.com/Pavs1605/SpringEmpDeptDemo/assets/18229871/785b153a-1c44-4d21-b78e-ac38acbe2206)

### Patron
![image](https://github.com/Pavs1605/SpringEmpDeptDemo/assets/18229871/b50ee32c-c498-4a5f-9d04-dcb8b19670f1)

### Borrowing Record
![image](https://github.com/Pavs1605/SpringEmpDeptDemo/assets/18229871/eb5ea195-194e-49d0-b1eb-a20e02411391)



## Test Report Results
Run tests using command - mvn test
* Output :
* main] .c.s.DirtiesContextTestExecutionListener : After test class: class [LibraryApplicationTests], class annotated with @DirtiesContext [false] with mode [null]
* [INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 9.416 s -- in com.books.library.LibraryApplicationTests
* [INFO] Running com.books.library.repository.BorrowingRecordRepositoryTest
* [INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.558 s -- in com.books.library.repository.BorrowingRecordRepositoryTest
* [INFO] Running com.books.library.service.BookServiceImplTest
* [INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.493 s -- in com.books.library.service.BookServiceImplTest
* [INFO] Running com.books.library.service.BorrowingRecordServiceImplTest
* [INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.178 s -- in com.books.library.service.BorrowingRecordServiceImplTest
* [INFO] Running com.books.library.service.PatronServiceImplTest
* [INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.037 s -- in com.books.library.service.PatronServiceImplTest
* [INFO]
* [INFO] Results:
* [INFO]
* [INFO] Tests run: 23, Failures: 0, Errors: 0, Skipped: 0
* [INFO]
* [INFO] ------------------------------------------------------------------------
* [INFO] BUILD SUCCESS
* [INFO] ------------------------------------------------------------------------
* [INFO] Total time:  16.332 s
* [INFO] Finished at: 2024-01-29T06:42:26+04:00
* [INFO] ------------------------------------------------------------------------


## Completed Items
Requirements:

* Entities - ## DONE
● Create entities for:
● Book: Includes attributes like ID, title, author, publication year, ISBN, etc.
● Patron: Contains details like ID, name, contact information, etc.
● Borrowing Record: Tracks the association between books and patrons, including borrowing and return dates.

* API Endpoints - ## DONE
● Implement RESTful endpoints to handle the following operations:
● Book management endpoints:
● GET /api/books: Retrieve a list of all books.
● GET /api/books/{id}: Retrieve details of a specific book by ID.
● POST /api/books: Add a new book to the library.
● PUT /api/books/{id}: Update an existing book's information.
● DELETE /api/books/{id}: Remove a book from the library.
● Patron management endpoints:
● GET /api/patrons: Retrieve a list of all patrons.
● GET /api/patrons/{id}: Retrieve details of a specific patron by ID.
● POST /api/patrons: Add a new patron to the system.
● PUT /api/patrons/{id}: Update an existing patron's information.
● DELETE /api/patrons/{id}: Remove a patron from the system.
● Borrowing endpoints:
● POST /api/borrow/{bookId}/patron/{patronId}: Allow a patron to borrow a book.
● PUT /api/return/{bookId}/patron/{patronId}: Record the return of a borrowed book by a patron.

* Data Storage: - ### DONE
● Use an appropriate database (e.g., H2, MySQL, PostgreSQL) to persist book, patron, and borrowing record details.
● Set up proper relationships between entities (e.g., one-to-many between books and borrowing records).

* Validation and Error Handling: ## DONE
● Implement input validation for API requests (e.g., validating required fields, data formats, etc.).
● Handle exceptions gracefully and return appropriate HTTP status codes and error messages.


* Transaction Management: ## DONE
● Implement declarative transaction management using Spring's @Transactional
annotation to ensure data integrity during critical operations.

* Testing: ## DONE
● Write unit tests to validate the functionality of API endpoints.
● Use testing frameworks like JUnit, Mockito, or SpringBootTest for testing.

* Documentation: ## DONE
● Provide clear documentation on how to run the application, interact with API
endpoints, and use any authentication if implemented.



