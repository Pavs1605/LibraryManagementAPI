package com.books.library.controller;

import com.books.library.model.Patron;
import com.books.library.service.PatronService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

@Api(value = "/api/patron", tags = "Patron Management", description = "Gives the list of API's to manage patrons")
@RestController
@RequestMapping("/api/patron")
@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH})
public class PatronController {

    @Autowired
    PatronService patronService;

    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all patrons available", description = "Returns list of patrons available")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The patron was not found")
    })
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

    @GetMapping(value="{patronId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all patrons by patron Id", description = "Returns single patron information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The patron was not found")
    })
    public Patron getPatron(@PathVariable("patronId") Long patronId) {
        return patronService.getPatron(patronId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Creates a patron", description = "Returns newly created patron")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "404", description = "Not found - The patron was not found")
    })
    public ResponseEntity<?> createPatron(@Valid @RequestBody Patron patronObj, BindingResult bindingResult) {
        Patron savedPatron = patronService.createPatron(patronObj);
        return ResponseEntity.ok(savedPatron);
    }

    @PutMapping(value="{patronId}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Updates a patron using patron id", description = "Returns updated  patron")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Updated"),
            @ApiResponse(responseCode = "404", description = "Not found - The patron was not found")
    })
    public Patron updatePatron(@PathVariable("patronId") Long patronId, @RequestBody Patron patronObj) {
        return patronService.updatePatron(patronId, patronObj);
    }

    @DeleteMapping("{patronId}")
    @Operation(summary = "Deletes a patron using patron id", description = "Deletes patron")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully Deleted/No content"),
            @ApiResponse(responseCode = "404", description = "Not found - The patron was not found")
    })
    public void deletePatron(@PathVariable("patronId") Long patronId) {
         patronService.deletePatron(patronId);
    }
}
