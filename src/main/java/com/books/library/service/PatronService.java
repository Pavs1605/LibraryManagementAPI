package com.books.library.service;

import com.books.library.model.Patron;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface PatronService {
    public Page<Patron> getAllPatrons(Pageable pageable);

    public Patron getPatron(Long patronId);

    public Patron createPatron(Patron patronObj);

    public Patron updatePatron(Long patronId, Patron patronObj);

    public void deletePatron(Long patronId);
}
