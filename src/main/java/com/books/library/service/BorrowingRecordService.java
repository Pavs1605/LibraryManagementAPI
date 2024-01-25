package com.books.library.service;

import com.books.library.model.BorrowingRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BorrowingRecordService {
    public Page<BorrowingRecord> getAllRecords(Pageable pageable);

    public Page<BorrowingRecord> getAllRecordsByBookIdAndPatronId(Long bookId,Long patronId,Pageable pageable);

    public BorrowingRecord borrowBook(Long bookId,Long patronId);

    public void returnBook(Long bookId,Long patronId);

    public Page<BorrowingRecord> getRecordsByPatronId(Long patronId, Pageable pageable);

    public Page<BorrowingRecord> getRecordsByBookId(Long bookId, Pageable pageable);


}
