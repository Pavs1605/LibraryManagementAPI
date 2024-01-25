package com.books.library.model;

import com.books.library.model.enums.BookState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import java.time.LocalDate;
@Entity
@Table(name = "borrowing_records")
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

    private Boolean bookAvailable;
    @Column(name = "book_state", nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'FREE'") // Adjust length as needed
    @Enumerated(EnumType.STRING)
    private BookState bookState;

    public String getPatronName() {
        return patron != null ? patron.getName() : null;
    }



    public String getBookTitle() {
        return book != null ? book.getTitle() : null;
    }


    public String getAuthor() {
        return  book != null ? book.getAuthor() : null;
    }



    public Boolean getBookAvailable() {
        return bookAvailable;
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

    public Boolean isBookAvailable() {
        return bookAvailable;
    }

    public void setBookAvailable(Boolean bookAvailable) {
        this.bookAvailable = bookAvailable;
    }

    public BookState getBookState() {
        return bookState;
    }

    public void setBookState(BookState bookState) {
        this.bookState = bookState;
    }
}
