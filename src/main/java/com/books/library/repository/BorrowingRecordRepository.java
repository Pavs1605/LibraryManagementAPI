package com.books.library.repository;

import com.books.library.model.Book;
import com.books.library.model.enums.BookState;
import com.books.library.model.BorrowingRecord;
import com.books.library.model.Patron;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
    public Page<BorrowingRecord> findByBook_BookIdAndPatron_PatronId(Long bookId, Long patronId, Pageable pageable);

    public List<BorrowingRecord> findByBook_BookIdAndPatron_PatronIdAndBookState(Long bookId, Long patronId, BookState bookState   );

    public Page<BorrowingRecord> findByBook_BookIdOrderByBorrowingDateDesc(Long bookId, Pageable pageable);
    public Page<BorrowingRecord> findByPatron_PatronIdOrderByBorrowingDateDesc(Long patronId, Pageable pageable);

    List<BorrowingRecord> findByBook_BookIdAndBookState(Long bookId, BookState bookState);

}
