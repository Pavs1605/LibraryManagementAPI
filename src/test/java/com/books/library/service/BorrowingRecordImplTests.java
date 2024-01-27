package com.books.library.service;

import com.books.library.exception.*;
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
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private Book testBook;
    private Patron testPatron;

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
        when(bookRepository.findByBookId(anyLong())).thenReturn(testBook);
        when(patronRepository.findByPatronId(anyLong())).thenReturn(testPatron);
        when(borrowingRecordRepository.findByBook_BookIdAndPatron_PatronIdAndBookState(anyLong(), anyLong(), eq(BookState.BORROWED))).thenReturn(new ArrayList<>());
        when(borrowingRecordRepository.findByBook_BookIdAndBookState(anyLong(), eq(BookState.BORROWED))).thenReturn(new ArrayList<>());
        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(testRecord);

        BorrowingRecord result = borrowingRecordService.borrowBook(1L, 1L);

        assertNotNull(result);
        assertEquals(testRecord, result);
        assertEquals(BookState.BORROWED, result.getBookState());
    }

    @Test
    void testReturnBook() {
        List<BorrowingRecord> records = new ArrayList<>();
        records.add(testRecord);
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
        Pageable pageable = mock(Pageable.class);
        Page<Book> bookPage = mock(Page.class);
        when(bookRepository.findAll(pageable)).thenReturn(bookPage);

        List<Book> borrowedBooks = new ArrayList<>();
        borrowedBooks.add(testBook);
        when(borrowingRecordRepository.findDistinctBooksBorrowed()).thenReturn(borrowedBooks);

        Page<Book> result = borrowingRecordService.getFreeBooks(pageable);

        assertNotNull(result);
        assertEquals(bookPage, result);
    }
}