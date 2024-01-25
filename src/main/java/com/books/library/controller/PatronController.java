package com.books.library.controller;

import com.books.library.model.Patron;
import com.books.library.service.PatronService;
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
@RequestMapping("/api/patron")
@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH})
public class PatronController {

    @Autowired
    PatronService patronService;

    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }

    @GetMapping()
    public ResponseEntity<Map<String, Object>> getAllPatrons(@PageableDefault(size = 10) Pageable pageable) {
        Page<Patron> resultPage = patronService.getAllPatrons(pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("cities", resultPage.getContent());
        response.put("currentPage", resultPage.getPageable().getPageNumber());
        response.put("currentSize", resultPage.getSize());
        response.put("totalPages", resultPage.getTotalPages());
        response.put("remainingElements", resultPage.getTotalElements() - (long) resultPage.getNumber() * resultPage.getSize());
        response.put("totalElements", resultPage.getTotalElements());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{patronId}")
    public Patron getPatron(@PathVariable("patronId") Long patronId) {
        return patronService.getPatron(patronId);
    }

    @PostMapping
    public ResponseEntity<?> createPatron(@Valid @RequestBody Patron patronObj, BindingResult bindingResult) {
        Patron savedPatron = patronService.createPatron(patronObj);
        return ResponseEntity.ok(savedPatron);
    }

    @PutMapping("{patronId}")
    public Patron updatePatron(@PathVariable("patronId") Long patronId, @RequestBody Patron patronObj) {
        return patronService.updatePatron(patronId, patronObj);
    }

    @DeleteMapping("{patronId}")
    public void deletePatron(@PathVariable("patronId") Long patronId) {
         patronService.deletePatron(patronId);
    }
}
