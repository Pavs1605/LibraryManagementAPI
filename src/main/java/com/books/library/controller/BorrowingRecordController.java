package com.books.library.controller;

import com.books.library.model.Book;
import com.books.library.model.BorrowingRecord;
import com.books.library.service.BorrowingRecordService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/api/records")
@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH})
@Api(value = "/api/records", tags = "Records History", description = "Gives the list of API's to borrow, return books and get history of the transactions")
public class BorrowingRecordController {
    @Autowired
    BorrowingRecordService borrowingRecordService;

    public BorrowingRecordController(BorrowingRecordService borrowingRecordService) {
        this.borrowingRecordService = borrowingRecordService;
    }

    @Operation(summary = "Get all transactions of books", description = "Returns list of transaction records of books being borrowed and deleted")
    @GetMapping(value = "/records/",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved")

    })
    public ResponseEntity<Map<String, Object>> getAllRecords(@PageableDefault(size = 10) Pageable pageable)
    {
        Page<BorrowingRecord> resultPage = borrowingRecordService.getAllRecords(pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("records", resultPage.getContent());
        response.put("currentPage", resultPage.getPageable().getPageNumber());
        response.put("currentSize", resultPage.getNumber());
        response.put("totalPages", resultPage.getTotalPages());
        response.put("remainingElements", resultPage.getTotalElements() - (long) resultPage.getNumber() * resultPage.getSize());
        response.put("totalElements", resultPage.getTotalElements());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


@PostMapping(value = "/borrow/{bookId}/patron/{patronId}", produces = MediaType.APPLICATION_JSON_VALUE)
@Operation(summary = "Borrow a book giving bookId & patronId", description = "Creates a transaction entry & Returns transaction details that book is assigned to patron")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully borrowed"),
        @ApiResponse(responseCode = "404", description = "Not found - The patron or book was not found"),
        @ApiResponse(responseCode = "400", description = "Bad request - Invalid owner, book cannot be returned, book not available")

})
    public BorrowingRecord borrowBook(@PathVariable Long bookId, @PathVariable Long patronId)
    {
        return borrowingRecordService.borrowBook(bookId, patronId);
    }

    @PutMapping(value = "/return/{bookId}/patron/{patronId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Return a book giving bookId & patronId", description = "Soft delete of the transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully returned/No content"),
            @ApiResponse(responseCode = "404", description = "Not found - The patron or book was not found"),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid owner, book cannot be returned, book not available")

    })
    public ResponseEntity<?> returnBook(@PathVariable Long bookId, @PathVariable Long patronId){
         borrowingRecordService.returnBook(bookId, patronId);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/book/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Gets the history of all transaction  records for a bookId", description = "Returns the list of transaction records for the book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The book was not found")
    })
    public ResponseEntity<Map<String, Object>> getRecordsByBookId(@PathVariable Long bookId, Pageable pageable)
    {
        Page<BorrowingRecord> resultPage = borrowingRecordService.getRecordsByBookId(bookId, pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("records", resultPage.getContent());
        response.put("currentPage", resultPage.getPageable().getPageNumber());
        response.put("currentSize", resultPage.getNumber());
        response.put("totalPages", resultPage.getTotalPages());
        response.put("remainingElements", resultPage.getTotalElements() - (long) resultPage.getNumber() * resultPage.getSize());
        response.put("totalElements", resultPage.getTotalElements());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/patron/{patronId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Gets the history of all transaction records for a patron using patronId", description = "Returns the list of transaction records for the patron")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The patron was not found")
    })
    public ResponseEntity<Map<String, Object>> getRecordsByPatronId(@PathVariable Long patronId, Pageable pageable)
    {
        Page<BorrowingRecord> resultPage = borrowingRecordService.getRecordsByPatronId(patronId, pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("records", resultPage.getContent());
        response.put("currentPage", resultPage.getPageable().getPageNumber());
        response.put("currentSize",resultPage.getNumber());
        response.put("totalPages", resultPage.getTotalPages());
        response.put("remainingElements", resultPage.getTotalElements() - (long) resultPage.getNumber() * resultPage.getSize());
        response.put("totalElements", resultPage.getTotalElements());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @GetMapping(value = "/book/{bookId}/patron/{patronId}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Gets the history of transaction records for a user who has taken same book multiple times", description = "Returns the list of transaction records for the combination of book and patron")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The patron or book was not found")
    })
    public ResponseEntity<Map<String, Object>> getRecordsByBookIdAndPatronId(@PathVariable Long patronId, @PathVariable Long bookId, Pageable pageable)
    {
        Page<BorrowingRecord> resultPage = borrowingRecordService.getAllRecordsByBookIdAndPatronId(patronId, bookId, pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("records", resultPage.getContent());
        response.put("currentPage", resultPage.getPageable().getPageNumber());
        response.put("currentSize", resultPage.getNumber());
        response.put("totalPages", resultPage.getTotalPages());
        response.put("remainingElements", resultPage.getTotalElements() - (long) resultPage.getNumber() * resultPage.getSize());
        response.put("totalElements", resultPage.getTotalElements());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/records/freeBooks/",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Gets the list of free books available in the system which are not borrowed", description = "Returns the list of free books available in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved")
    })
    public ResponseEntity<Map<String, Object>> getFreeBooks(Pageable pageable)
    {
        Page<Book> resultPage = borrowingRecordService.getFreeBooks( pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("records", resultPage.getContent());
        response.put("currentPage", resultPage.getPageable().getPageNumber());
        response.put("currentSize",resultPage.getNumber());
        response.put("totalPages", resultPage.getTotalPages());
        response.put("remainingElements", resultPage.getTotalElements() - (long) resultPage.getNumber() * resultPage.getSize());
        response.put("totalElements", resultPage.getTotalElements());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
