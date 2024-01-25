package com.books.library.serviceImpl;

import com.books.library.exception.BookNotFoundException;
import com.books.library.exception.PatronNotFoundException;
import com.books.library.model.Book;
import com.books.library.model.Patron;
import com.books.library.repository.PatronRepository;
import com.books.library.service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatronServiceImpl implements PatronService {
    @Autowired
    PatronRepository patronRepository;

    public PatronServiceImpl(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    @Override
    public Page<Patron> getAllPatrons(Pageable pageable) {
        return patronRepository.findAll(pageable);
    }

    @Override
    public Patron getPatron(Long patronId) {
        return findPatronById(patronId);
    }

    @Override
    public Patron createPatron(Patron patronObj) {

        return patronRepository.save(patronObj);
    }

    @Override
    public Patron updatePatron(Long patronId, Patron patronObj) {

        Patron patronExist = findPatronById(patronId);

        Optional.ofNullable(patronObj.getAddress()).ifPresent(patronExist::setAddress);
        Optional.ofNullable(patronObj.getContactNo()).ifPresent(patronExist::setContactNo);
        Optional.ofNullable(patronObj.getName()).ifPresent(patronExist::setName);
        Optional.ofNullable(patronObj.getContactNo()).ifPresent(patronExist::setContactNo);


        return  patronRepository.save(patronExist);
    }

    @Override
    public void deletePatron(Long patronId) {

        Patron patron = findPatronById(patronId);
        patronRepository.delete(patron);
    }

    private Patron findPatronById(Long patronId)
    {
        Optional<Patron> patron = patronRepository.findById(patronId);
        if(patron.isEmpty())
            throw new PatronNotFoundException(patronId);
        else
            return patron.get();
    }
}
