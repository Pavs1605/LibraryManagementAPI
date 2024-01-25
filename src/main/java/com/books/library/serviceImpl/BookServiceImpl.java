package com.books.library.serviceImpl;

import com.books.library.exception.BookNotFoundException;
import com.books.library.model.Book;
import com.books.library.repository.BookRepository;
import com.books.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

@Component
public class BookServiceImpl implements BookService {
    @Autowired
    BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Book getBook(Long bookId) {
        return findBookById(bookId);
    }

    @Override
    public Book createBook(Book bookObj) {
       return  bookRepository.save(bookObj);

    }

    @Override
    public Book updateBook(Long bookId, Book bookObj) {

        Book existingBook = findBookById(bookId);

        //only sets the fields if the value is not null
        Optional.ofNullable(bookObj.getTitle()).ifPresent(existingBook::setTitle);
        Optional.ofNullable(bookObj.getAuthor()).ifPresent(existingBook::setAuthor);
        Optional.ofNullable(bookObj.getPublicationYear()).ifPresent(existingBook::setPublicationYear);
        Optional.ofNullable(bookObj.getSerialNumber()).ifPresent(existingBook::setSerialNumber);
        Optional.ofNullable(bookObj.getIsbnNumber()).ifPresent(existingBook::setIsbnNumber);
        Optional.ofNullable(bookObj.getDescription()).ifPresent(existingBook::setDescription);

       return  bookRepository.save(existingBook);
    }

    @Override
    public void deleteBook(Long bookId) {
        Book bookObj = findBookById(bookId);
         bookRepository.delete(bookObj);

    }

    private Book findBookById(Long bookId)
    {
       Optional<Book> book = bookRepository.findById(bookId);
        if(book.isEmpty())
            throw new BookNotFoundException(bookId);
        else
            return book.get();
    }
}
