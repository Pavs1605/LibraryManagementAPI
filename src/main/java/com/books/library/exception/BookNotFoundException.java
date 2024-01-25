package com.books.library.exception;

public class BookNotFoundException  extends RuntimeException {

    public BookNotFoundException(Long bookId) {
        super("Book not found with id: " + bookId);
    }
}
