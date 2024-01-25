package com.books.library.repository;

import com.books.library.model.Book;
import com.books.library.model.Patron;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatronRepository extends JpaRepository<Patron, Long> {
    Page<Patron> findAll(Pageable pageable);

    Patron findByPatronId(Long patronId);

}
