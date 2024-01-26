package com.books.library.exception;

public class BookCannotBeReturnedException extends RuntimeException{
    public BookCannotBeReturnedException(String message) {
        super(message);
    }
}
