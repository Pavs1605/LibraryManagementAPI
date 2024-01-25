package com.books.library.controller;

import com.books.library.model.BorrowingRecord;
import com.books.library.service.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH})
public class BorrowingRecordController {
    @Autowired
    BorrowingRecordService borrowingRecordService;

    public BorrowingRecordController(BorrowingRecordService borrowingRecordService) {
        this.borrowingRecordService = borrowingRecordService;
    }

    @GetMapping("/records/")
    public ResponseEntity<Map<String, Object>> getAllRecords(@PageableDefault(size = 10) Pageable pageable)
    {
        Page<BorrowingRecord> resultPage = borrowingRecordService.getAllRecords(pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("records", resultPage.getContent());
        response.put("currentPage", resultPage.getPageable().getPageNumber());
        response.put("currentSize", resultPage.getSize());
        response.put("totalPages", resultPage.getTotalPages());
        response.put("remainingElements", resultPage.getTotalElements() - (long) resultPage.getNumber() * resultPage.getSize());
        response.put("totalElements", resultPage.getTotalElements());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


@PostMapping("/borrow/{bookId}/patron/{patronId}")
    public BorrowingRecord borrowBook(@PathVariable Long bookId, @PathVariable Long patronId)
    {
        return borrowingRecordService.borrowBook(bookId, patronId);
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
        public ResponseEntity<?> returnBook(@PathVariable Long bookId, @PathVariable Long patronId){
         borrowingRecordService.returnBook(bookId, patronId);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/records/{bookId}")
    public ResponseEntity<Map<String, Object>> getRecordsByBookId(@PathVariable Long bookId, Pageable pageable)
    {
        Page<BorrowingRecord> resultPage = borrowingRecordService.getRecordsByBookId(bookId, pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("records", resultPage.getContent());
        response.put("currentPage", resultPage.getPageable().getPageNumber());
        response.put("currentSize", resultPage.getSize());
        response.put("totalPages", resultPage.getTotalPages());
        response.put("remainingElements", resultPage.getTotalElements() - (long) resultPage.getNumber() * resultPage.getSize());
        response.put("totalElements", resultPage.getTotalElements());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/records/{patronId}")
    public ResponseEntity<Map<String, Object>> getRecordsByPatronId(@PathVariable Long patronId, Pageable pageable)
    {
        Page<BorrowingRecord> resultPage = borrowingRecordService.getRecordsByPatronId(patronId, pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("records", resultPage.getContent());
        response.put("currentPage", resultPage.getPageable().getPageNumber());
        response.put("currentSize", resultPage.getSize());
        response.put("totalPages", resultPage.getTotalPages());
        response.put("remainingElements", resultPage.getTotalElements() - (long) resultPage.getNumber() * resultPage.getSize());
        response.put("totalElements", resultPage.getTotalElements());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @GetMapping("/records/book/{bookId}/patron/{patronId}")
    public ResponseEntity<Map<String, Object>> getRecordsByBookIdAndPatronId(@PathVariable Long patronId, @PathVariable Long bookId, Pageable pageable)
    {
        Page<BorrowingRecord> resultPage = borrowingRecordService.getAllRecordsByBookIdAndPatronId(patronId, bookId, pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("records", resultPage.getContent());
        response.put("currentPage", resultPage.getPageable().getPageNumber());
        response.put("currentSize", resultPage.getSize());
        response.put("totalPages", resultPage.getTotalPages());
        response.put("remainingElements", resultPage.getTotalElements() - (long) resultPage.getNumber() * resultPage.getSize());
        response.put("totalElements", resultPage.getTotalElements());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
