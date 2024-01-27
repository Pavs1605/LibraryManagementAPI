package com.books.library.service;

import com.books.library.exception.BookNotFoundException;
import com.books.library.model.Book;
import com.books.library.repository.BookRepository;
import com.books.library.serviceImpl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book testBook;

    @BeforeEach
    void setUp() {
        testBook = new Book();
        testBook.setBookId(1L);
        testBook.setTitle("Test Book");
        testBook.setAuthor("Test Author");
        testBook.setPublicationYear(2022L);
        testBook.setSerialNumber(123456L);
        testBook.setIsbnNumber("978-3-16-148410-0");
        testBook.setDescription("Test Description");
    }

    @Test
    void testGetAllBooks() {
        Pageable pageable = mock(Pageable.class);
        Page<Book> page = mock(Page.class);
        when(bookRepository.findAll(pageable)).thenReturn(page);

        Page<Book> result = bookService.getAllBooks(pageable);

        assertNotNull(result);
        assertEquals(page, result);
    }

    @Test
    void testGetBook() {
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(testBook));

        Book result = bookService.getBook(bookId);

        assertNotNull(result);
        assertEquals(testBook, result);
    }

    @Test
    void testCreateBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(testBook);

        Book result = bookService.createBook(testBook);

        assertNotNull(result);
        assertEquals(testBook, result);
    }

    @Test
    void testUpdateBook() {
        Long bookId = 1L;
        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Title");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(testBook));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        Book result = bookService.updateBook(bookId, updatedBook);

        assertNotNull(result);
        assertEquals(updatedBook.getTitle(), result.getTitle());
    }

    @Test
    void testDeleteBook() {
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(testBook));

        assertDoesNotThrow(() -> bookService.deleteBook(bookId));
        verify(bookRepository, times(1)).delete(testBook);
    }

    @Test
    void testDeleteBook_ThrowsBookNotFoundException() {
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(bookId));
    }
}
