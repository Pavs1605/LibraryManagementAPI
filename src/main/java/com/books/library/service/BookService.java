package com.books.library.service;

import com.books.library.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public interface BookService {

    public Page<Book> getAllBooks(Pageable pageable);

    public Book getBook(Long bookId);

    public Book createBook(Book bookObj);

    public Book updateBook(Long bookId, Book bookObj);

    public void deleteBook(Long bookId);



}
