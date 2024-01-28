package com.books.library.serviceImpl;

import com.books.library.exception.*;
import com.books.library.model.Book;
import com.books.library.model.BorrowingRecord;
import com.books.library.model.Patron;
import com.books.library.model.enums.BookState;
import com.books.library.repository.BookRepository;
import com.books.library.repository.BorrowingRecordRepository;
import com.books.library.repository.PatronRepository;
import com.books.library.service.BorrowingRecordService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowingRecordServiceImpl implements BorrowingRecordService {
    @Autowired
    BorrowingRecordRepository borrowingRecordRepository;
    BookRepository bookRepository;
    PatronRepository patronRepository;


    public BorrowingRecordServiceImpl(BorrowingRecordRepository borrowingRecordRepository, BookRepository bookRepository, PatronRepository patronRepository) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
    }



    @Override
    public Page<BorrowingRecord> getAllRecords(Pageable pageable) {
       return borrowingRecordRepository.findAll(pageable);
    }

    @Override
    public Page<BorrowingRecord> getAllRecordsByBookIdAndPatronId(Long bookId, Long patronId, Pageable pageable) {
        return borrowingRecordRepository.findByBook_BookIdAndPatron_PatronId(bookId, patronId, pageable);
    }

    //smae person cannot borrow same book
    //if a book is not available, no one can have it
    //if a book is available, then you can borrow
    //person does not exist or book does not exist, return appropriate error
    @Override
    @Transactional
    public BorrowingRecord borrowBook(Long bookId, Long patronId) {
        BorrowingRecord rec = new BorrowingRecord();


        //check if book exist
        doesBookExist(bookId);

        //check if patron exist
        doesPatronExist(patronId);

        //check if available to borrow
        if (isBookFreeToBorrow(bookId, patronId)) {
            rec.setBook(bookRepository.findByBookId(bookId));
            rec.setPatron(patronRepository.findByPatronId(patronId));
            rec.setBorrowingDate(LocalDate.now());
            rec.setBookState(BookState.BORROWED);
            borrowingRecordRepository.save(rec);
        }
        return rec;
    }


    @Override
    @Transactional
    public void returnBook(Long bookId, Long patronId) {
        BorrowingRecord obj = new BorrowingRecord();
        //check if book exist
        doesBookExist(bookId);

        //check if patron exist
        doesPatronExist(patronId);


        //check if book is already returned
        List<BorrowingRecord> rec = borrowingRecordRepository.findByBook_BookIdAndPatron_PatronIdAndBookState(bookId, patronId, BookState.BORROWED);

        if(rec != null && rec.size() == 1)
        {
            obj = rec.get(0);
            obj.setBookState(BookState.RETURNED);
            obj.setReturnDate(LocalDate.now());
            borrowingRecordRepository.save(obj);
        }
        else {
            throw new BookCannotBeReturnedException("Book cannot be returned");
        }


    }

    @Override
    public Page<BorrowingRecord> getRecordsByPatronId(Long patronId, Pageable pageable) {
         return  borrowingRecordRepository.findByPatron_PatronIdOrderByBorrowingDateDesc(patronId, pageable);

    }

    @Override
    public Page<BorrowingRecord> getRecordsByBookId(Long bookId, Pageable pageable) {
           return  borrowingRecordRepository.findByBook_BookIdOrderByBorrowingDateDesc(bookId, pageable);

    }

    @Override
    public Page<Book> getFreeBooks(Pageable pageable) {
        Page<Book> bookList = bookRepository.findAll(pageable);
        List<Book> borrowingRecords = borrowingRecordRepository.findDistinctBooksBorrowed();

        List<Book> booksToRemove = new ArrayList<>(borrowingRecords);
        List<Book> allBooksList = new ArrayList<>(bookList.getContent());
        allBooksList.removeAll(booksToRemove);

        return convertListToPage(allBooksList, pageable);
    }

    private Page<Book> convertListToPage(List<Book> bookList, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Book> pageList;

        if (startItem >= bookList.size()) {
            pageList = Collections.emptyList(); // No items in the page
        } else {
            int toIndex = Math.min(startItem + pageSize, bookList.size());
            toIndex = Math.max(toIndex, startItem + 1);
            pageList = bookList.subList(startItem, toIndex);
        }


        return new PageImpl<>(pageList, pageable, bookList.size());
    }


    private void doesBookExist(Long bookId) {
        Book book = bookRepository.findByBookId(bookId);

        if (book == null)
            throw new BookNotFoundException(bookId);

    }

    private void doesPatronExist(Long patronId) {
        Patron patron = patronRepository.findByPatronId(patronId);

        if (patron == null)
            throw new PatronNotFoundException(patronId);

    }

    private boolean isBookFreeToBorrow(Long bookId, Long patronId) {
        List<BorrowingRecord> rec = borrowingRecordRepository.findByBook_BookIdAndPatron_PatronIdAndBookState(bookId, patronId, BookState.BORROWED);
        List<BorrowingRecord> obj = borrowingRecordRepository.findByBook_BookIdAndBookState(bookId, BookState.BORROWED);

        //same person cannot borrow
        if (rec != null && rec.size() == 1)
            throw new InvalidBookOwnerException();
        //book not available
        if (obj != null && obj.size() == 1)
            throw new BookNotAvailableException("Book not available for borrowing");

        return true;

    }
}
