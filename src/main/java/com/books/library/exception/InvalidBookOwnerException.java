package com.books.library.exception;

public class InvalidBookOwnerException extends RuntimeException{

    public InvalidBookOwnerException()
    {
        super("Borrower and Owner cannot be same");
    }
}
