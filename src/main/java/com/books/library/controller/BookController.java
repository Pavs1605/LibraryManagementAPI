package com.books.library.controller;

import com.books.library.model.Book;
import com.books.library.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH})
@Api( tags="Book Management", description = "Gives a list of operations for managing books")
public class BookController {
    @Autowired
    BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all books available", description = "Returns list of books available")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The book was not found")
    })
    public ResponseEntity<Map<String, Object>> getAllBooks(@PageableDefault(size = 10) Pageable pageable) {
        Page<Book> resultPage = bookService.getAllBooks(pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("books", resultPage.getContent());
        response.put("currentPage", resultPage.getPageable().getPageNumber());
        response.put("currentSize", resultPage.getSize());
        response.put("totalPages", resultPage.getTotalPages());
        response.put("remainingElements", resultPage.getTotalElements() - (long) resultPage.getNumber() * resultPage.getSize());
        response.put("totalElements", resultPage.getTotalElements());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value="/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a single book by book Id", description = "Returns single book with book details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The book was not found")
    })
    public Book getBook(@PathVariable("bookId") Long bookId) {
        return bookService.getBook(bookId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Creates a book in the system ", description = "Creates a book in the system and returns newly created book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "404", description = "Not found - The book was not found")
    })
    public ResponseEntity<?> createBook(@Valid @RequestBody Book bookObj, BindingResult bindingResult) {

        Book savedBook = bookService.createBook(bookObj);
        return ResponseEntity.ok(savedBook);
    }

    @PutMapping(value="/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Updates a book using bookId", description = "Updates a book in the system using bookId and returns updated book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "404", description = "Not found - The book was not found")
    })
    public Book updateBook(@PathVariable("bookId") Long bookId, @RequestBody Book bookObj) {
        return bookService.updateBook(bookId, bookObj);
    }

    @DeleteMapping("{bookId}")
    @Operation(summary = "Deletes a book  using bookId", description = "Deletes a book in the system using bookId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted/No content"),
            @ApiResponse(responseCode = "404", description = "Not found - The book was not found")
    })
    public void deleteBook(@PathVariable("bookId") Long bookId) {
         bookService.deleteBook(bookId);
    }

}
