package com.books.library.repository;

import com.books.library.model.Book;
import com.books.library.model.BorrowingRecord;
import com.books.library.model.enums.BookState;
import com.books.library.model.Patron;
import com.books.library.repository.BorrowingRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BorrowingRecordRepositoryTest {

    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @Test
    public void testFindByBook_BookIdAndPatron_PatronId() {
        // Mock data
        Pageable pageable = Pageable.unpaged();
        Page<BorrowingRecord> expectedPage = mockPage();

        // Mock behavior
        when(borrowingRecordRepository.findByBook_BookIdAndPatron_PatronId(anyLong(), anyLong(), any(Pageable.class)))
                .thenReturn(expectedPage);

        // Call the method under test
        Page<BorrowingRecord> result = borrowingRecordRepository.findByBook_BookIdAndPatron_PatronId(1L, 2L, pageable);

        // Verify behavior
        assertEquals(expectedPage, result);
    }

    @Test
    public void testFindByBook_BookIdAndPatron_PatronIdAndBookState() {
        // Mock data
        List<BorrowingRecord> expectedRecords = new ArrayList<>();

        // Mock behavior
        when(borrowingRecordRepository.findByBook_BookIdAndPatron_PatronIdAndBookState(anyLong(), anyLong(), eq(BookState.BORROWED)))
                .thenReturn(expectedRecords);

        // Call the method under test
        List<BorrowingRecord> result = borrowingRecordRepository.findByBook_BookIdAndPatron_PatronIdAndBookState(1L, 2L, BookState.BORROWED);

        // Verify behavior
        assertEquals(expectedRecords, result);
    }

    // Helper method to mock a Page
    private Page<BorrowingRecord> mockPage() {
        return new Page<BorrowingRecord>() {
            @Override
            public int getTotalPages() {
                return 1;
            }

            @Override
            public long getTotalElements() {
                return 1;
            }

            @Override
            public <U> Page<U> map(java.util.function.Function<? super BorrowingRecord, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<BorrowingRecord> getContent() {
                return null;
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<BorrowingRecord> iterator() {
                return null;
            }
        };
    }
}