package com.books.library.controller;

import com.books.library.model.Book;
import com.books.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH})
public class BookController {
    @Autowired
    BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping()
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

    @GetMapping("{bookId}")
    public Book getBook(@PathVariable("bookId") Long bookId) {
        return bookService.getBook(bookId);
    }

    @PostMapping
    public ResponseEntity<?> createBook(@Valid @RequestBody Book bookObj, BindingResult bindingResult) {

        Book savedBook = bookService.createBook(bookObj);
        return ResponseEntity.ok(savedBook);
    }

    @PutMapping("{bookId}")
    public Book updateBook(@PathVariable("bookId") Long bookId, @RequestBody Book bookObj) {
        return bookService.updateBook(bookId, bookObj);
    }

    @DeleteMapping("{bookId}")
    public void deleteBook(@PathVariable("bookId") Long bookId) {
         bookService.deleteBook(bookId);
    }

}
