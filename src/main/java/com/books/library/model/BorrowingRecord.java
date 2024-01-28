package com.books.library.model;

import com.books.library.model.enums.BookState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "borrowing_records", uniqueConstraints = @UniqueConstraint(columnNames={"book_id", "patron_id", "borrowingDate"}))
public class BorrowingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long borrowingId;

    @ManyToOne
    @JoinColumn(name = "bookId")
    @JsonIgnore
    private Book book;
    @ManyToOne
    @JoinColumn(name = "patronId")
    @JsonIgnore
    private Patron patron;
    @Transient
    String patronName;

    @Transient
    String bookTitle;
    @Transient
    String author;

    private LocalDate borrowingDate;
    private LocalDate returnDate;


    @Column(name = "book_state", nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'FREE'") // Adjust length as needed
    @Enumerated(EnumType.STRING)
    private BookState bookState;

    @Transient
    public Long bookId;



    @Transient
    public Long patronId;

    public BorrowingRecord(){

    }

    public BorrowingRecord(Book book, Patron patron, LocalDate borrowingDate, LocalDate returnDate, BookState bookState) {
        this.book = book;
        this.patron = patron;
        this.borrowingDate = borrowingDate;
        this.returnDate = returnDate;
        this.bookState = bookState;
    }
    public String getPatronName() {
        return patron != null ? patron.getName() : null;
    }

    public Long getBookId() {
        return book != null ? book.getBookId() : null;

    }

    public Long getPatronId() {
        return patron != null ? patron.getPatronId() : null;
    }

    public String getBookTitle() {
        return book != null ? book.getTitle() : null;
    }


    public String getAuthor() {
        return  book != null ? book.getAuthor() : null;
    }



    public Long getBorrowingId() {
        return borrowingId;
    }

    public void setBorrowingId(Long borrowingId) {
        this.borrowingId = borrowingId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Patron getPatron() {
        return patron;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }

    public LocalDate getBorrowingDate() {
        return borrowingDate;
    }

    public void setBorrowingDate(LocalDate borrowingDate) {
        this.borrowingDate = borrowingDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }


    public BookState getBookState() {
        return bookState;
    }

    public void setBookState(BookState bookState) {
        this.bookState = bookState;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        BorrowingRecord other = (BorrowingRecord) obj;
        return Objects.equals(this.bookId, other.bookId) &&
                Objects.equals(this.borrowingId, other.borrowingId) &&
                Objects.equals(this.patronId, other.patronId) &&
                Objects.equals(this.bookState, other.bookState) &&
                Objects.equals(this.borrowingDate, other.borrowingDate) &&
                Objects.equals(this.returnDate, other.returnDate);
    }
}
