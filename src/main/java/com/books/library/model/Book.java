package com.books.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookId")
    Long bookId;
    @Column(unique = true)
    String title;
    @Column(unique = true)
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Invalid author name")
    String author;
    @Pattern(regexp = "^[0-9]{4}+$", message = "Invalid publication year")
    Long publicationYear;
    @Pattern(regexp = "^[0-9-]+$", message = "Invalid ISBN number")
    String isbnNumber;
    @Pattern(regexp = "\\d+", message = "Serial number must be a numeric value")
    Long serialNumber;
    String description;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Long publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getIsbnNumber() {
        return isbnNumber;
    }

    public void setIsbnNumber(String isbnNumber) {
        this.isbnNumber = isbnNumber;
    }

    public Long getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Long serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }






}
