package com.books.library.service;

import com.books.library.exception.BookNotAvailableException;
import com.books.library.exception.InvalidBookOwnerException;
import com.books.library.exception.PatronNotFoundException;
import com.books.library.model.Book;
import com.books.library.model.BorrowingRecord;
import com.books.library.model.Patron;
import com.books.library.model.enums.BookState;
import com.books.library.repository.BookRepository;
import com.books.library.repository.BorrowingRecordRepository;
import com.books.library.repository.PatronRepository;
import com.books.library.serviceImpl.BorrowingRecordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BorrowingRecordServiceImplTest {

    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PatronRepository patronRepository;

    @InjectMocks
    private BorrowingRecordServiceImpl borrowingRecordService;

    private BorrowingRecord testRecord;
    private BorrowingRecord secondTestRecord;
    private Book testBook;
    private Book secondTestBook;

    private Patron testPatron;
    private Patron secondTestPatron;


    @BeforeEach
    void setUp() {
        testBook = new Book();
        testBook.setBookId(1L);
        testBook.setTitle("Test Book");

        testPatron = new Patron();
        testPatron.setPatronId(1L);
        testPatron.setName("Test Patron");

        testRecord = new BorrowingRecord();
        testRecord.setBorrowingId(1L);
        testRecord.setBook(testBook);
        testRecord.setPatron(testPatron);
        testRecord.setBorrowingDate(LocalDate.now());
        testRecord.setBookState(BookState.BORROWED);

        secondTestBook = new Book();
        secondTestBook.setBookId(2L);
        secondTestBook.setTitle("Second Book");

        secondTestPatron = new Patron();
        secondTestPatron.setPatronId(2L);
        secondTestPatron.setName("Second Patron");

        secondTestRecord = new BorrowingRecord();
        secondTestRecord.setBorrowingId(2L);
        secondTestRecord.setBook(secondTestBook);
        secondTestRecord.setPatron(secondTestPatron);
        secondTestRecord.setBorrowingDate(LocalDate.now());
        secondTestRecord.setBookState(BookState.BORROWED);
    }

    @Test
    void testGetAllRecords() {
        Pageable pageable = mock(Pageable.class);
        Page<BorrowingRecord> page = mock(Page.class);
        when(borrowingRecordRepository.findAll(pageable)).thenReturn(page);

        Page<BorrowingRecord> result = borrowingRecordService.getAllRecords(pageable);

        assertNotNull(result);
        assertEquals(page, result);
    }

    @Test
    void testBorrowBook() {
        List<BorrowingRecord> records = new ArrayList<>();
        records.add(testRecord);
        when(bookRepository.findByBookId(anyLong())).thenReturn(secondTestBook);
        when(patronRepository.findByPatronId(anyLong())).thenReturn(secondTestPatron);
        when(borrowingRecordRepository.findByBook_BookIdAndPatron_PatronIdAndBookState(anyLong(), anyLong(), eq(BookState.BORROWED))).thenReturn(new ArrayList<>());
        when(borrowingRecordRepository.findByBook_BookIdAndBookState(anyLong(), eq(BookState.BORROWED))).thenReturn(new ArrayList<>());


        // Call the method under test
        BorrowingRecord result = borrowingRecordService.borrowBook(2L, 2L);

        // Assertions
        assertNotNull(result);
        assertEquals(secondTestRecord.getBook(), result.getBook());
        assertEquals(secondTestRecord.getPatron(), result.getPatron());
        assertEquals(LocalDate.now(), result.getBorrowingDate());
        assertEquals(BookState.BORROWED, result.getBookState());
    }

    @Test
    void testBorrowBookAPI_AlreadyBorrowedBook() {
        List<BorrowingRecord> records = new ArrayList<>();
        records.add(testRecord);
        when(bookRepository.findByBookId(anyLong())).thenReturn(testBook);
        when(patronRepository.findByPatronId(anyLong())).thenReturn(testPatron);
        when(borrowingRecordRepository.findByBook_BookIdAndPatron_PatronIdAndBookState(anyLong(), anyLong(), eq(BookState.BORROWED))).thenReturn(records);
        when(borrowingRecordRepository.findByBook_BookIdAndBookState(anyLong(), eq(BookState.BORROWED))).thenReturn(new ArrayList<>());

            // Assertions
        assertThrows(InvalidBookOwnerException.class, () -> {
            borrowingRecordService.borrowBook(1L, 1L);
        });

    }

    @Test
    void testBorrowBookAPI_TwoPeopleBuyingSameBook() {

        List<BorrowingRecord> records = new ArrayList<>();
        records.add(testRecord);
        when(bookRepository.findByBookId(anyLong())).thenReturn(testBook);
        when(patronRepository.findByPatronId(anyLong())).thenReturn(testPatron);
        when(borrowingRecordRepository.findByBook_BookIdAndPatron_PatronIdAndBookState(anyLong(), anyLong(), eq(BookState.BORROWED))).thenReturn(records);
        when(borrowingRecordRepository.findByBook_BookIdAndBookState(anyLong(), eq(BookState.BORROWED))).thenReturn(records);

        // Assertions
        assertThrows(BookNotAvailableException.class, () -> {
            borrowingRecordService.borrowBook(1L, 2L);
        });

    }



    @Test
    void testReturnBook() {
        List<BorrowingRecord> records = new ArrayList<>();
        records.add(testRecord);
        when(bookRepository.findByBookId(anyLong())).thenReturn(testBook);
        when(patronRepository.findByPatronId(anyLong())).thenReturn(testPatron);
        when(borrowingRecordRepository.findByBook_BookIdAndPatron_PatronIdAndBookState(anyLong(), anyLong(), eq(BookState.BORROWED))).thenReturn(records);

        borrowingRecordService.returnBook(1L, 1L);

        assertEquals(BookState.RETURNED, testRecord.getBookState());
        assertNotNull(testRecord.getReturnDate());
    }

    @Test
    void testGetRecordsByPatronId() {
        Pageable pageable = mock(Pageable.class);
        Page<BorrowingRecord> page = mock(Page.class);
        when(borrowingRecordRepository.findByPatron_PatronIdOrderByBorrowingDateDesc(anyLong(), eq(pageable))).thenReturn(page);

        Page<BorrowingRecord> result = borrowingRecordService.getRecordsByPatronId(1L, pageable);

        assertNotNull(result);
        assertEquals(page, result);
    }

    @Test
    void testGetRecordsByBookId() {
        Pageable pageable = mock(Pageable.class);
        Page<BorrowingRecord> page = mock(Page.class);
        when(borrowingRecordRepository.findByBook_BookIdOrderByBorrowingDateDesc(anyLong(), eq(pageable))).thenReturn(page);

        Page<BorrowingRecord> result = borrowingRecordService.getRecordsByBookId(1L, pageable);

        assertNotNull(result);
        assertEquals(page, result);
    }

    @Test
    void testGetFreeBooks() {
        // Mocking the pageable object
        Pageable pageable = mock(Pageable.class);

        // Mocking the list of books and the page
        List<Book> allBooks = Arrays.asList(testBook, secondTestBook);
        Page<Book> bookPage = new PageImpl<>(allBooks, pageable, allBooks.size());

        // Mocking borrowed books
        List<Book> borrowedBooks = Arrays.asList(testBook); // Assuming testBook is borrowed
        when(borrowingRecordRepository.findDistinctBooksBorrowed()).thenReturn(borrowedBooks);

        // Mocking bookRepository to return all books
        when(bookRepository.findAll(pageable)).thenReturn(bookPage);

        // Calling the method under test
        Page<Book> result = borrowingRecordService.getFreeBooks(pageable);

        // Verifying the result
        assertNotNull(result);
        assertEquals(1, result.getContent().size()); // Assuming two books are not borrowed
        assertTrue(result.getContent().contains(secondTestBook)); // Asserting one of the not borrowed books
    }
}