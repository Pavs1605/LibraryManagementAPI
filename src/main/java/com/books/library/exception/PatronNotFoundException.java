package com.books.library.exception;

public class PatronNotFoundException extends RuntimeException {

    public PatronNotFoundException(Long patronId) {
        super("Patron not found with id: " + patronId);
    }
}