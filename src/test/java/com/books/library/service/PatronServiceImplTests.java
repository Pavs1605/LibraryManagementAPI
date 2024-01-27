package com.books.library.service;

import com.books.library.exception.PatronNotFoundException;
import com.books.library.model.Patron;
import com.books.library.repository.PatronRepository;
import com.books.library.serviceImpl.PatronServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatronServiceImplTest {

    @Mock
    private PatronRepository patronRepository;

    @InjectMocks
    private PatronServiceImpl patronService;

    private Patron testPatron;

    @BeforeEach
    void setUp() {
        testPatron = new Patron();
        testPatron.setPatronId(1L);
        testPatron.setName("Test Patron");
        testPatron.setAddress("Test Address");
        testPatron.setContactNo("1234567890");
    }

    @Test
    void testGetAllPatrons() {
        Pageable pageable = mock(Pageable.class);
        Page<Patron> page = mock(Page.class);
        when(patronRepository.findAll(pageable)).thenReturn(page);

        Page<Patron> result = patronService.getAllPatrons(pageable);

        assertNotNull(result);
        assertEquals(page, result);
    }

    @Test
    void testGetPatron() {
        Long patronId = 1L;
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(testPatron));

        Patron result = patronService.getPatron(patronId);

        assertNotNull(result);
        assertEquals(testPatron, result);
    }

    @Test
    void testCreatePatron() {
        when(patronRepository.save(any(Patron.class))).thenReturn(testPatron);

        Patron result = patronService.createPatron(testPatron);

        assertNotNull(result);
        assertEquals(testPatron, result);
    }

    @Test
    void testUpdatePatron() {
        Long patronId = 1L;
        Patron updatedPatron = new Patron();
        updatedPatron.setName("Updated Name");

        when(patronRepository.findById(patronId)).thenReturn(Optional.of(testPatron));
        when(patronRepository.save(any(Patron.class))).thenReturn(updatedPatron);

        Patron result = patronService.updatePatron(patronId, updatedPatron);

        assertNotNull(result);
        assertEquals(updatedPatron.getName(), result.getName());
    }

    @Test
    void testDeletePatron() {
        Long patronId = 1L;
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(testPatron));

        assertDoesNotThrow(() -> patronService.deletePatron(patronId));
        verify(patronRepository, times(1)).delete(testPatron);
    }

    @Test
    void testDeletePatron_ThrowsNotFoundException() {
        Long patronId = 1L;
        when(patronRepository.findById(patronId)).thenReturn(Optional.empty());

        assertThrows(PatronNotFoundException.class, () -> patronService.deletePatron(patronId));
    }
}