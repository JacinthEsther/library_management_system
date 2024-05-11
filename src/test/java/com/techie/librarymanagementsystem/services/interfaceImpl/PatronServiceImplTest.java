package com.techie.librarymanagementsystem.services.interfaceImpl;

import com.techie.librarymanagementsystem.dtos.PatronDTO;
import com.techie.librarymanagementsystem.entities.Patron;
import com.techie.librarymanagementsystem.repositories.PatronRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class PatronServiceImplTest {

    @Mock
    private PatronRepository patronRepository;

    @InjectMocks
    private PatronServiceImpl patronService;

    @Test
    void getAllPatrons() {

        Patron patron1 = new Patron();
        patron1.setId(1L);
        patron1.setName("John");
        patron1.setContactInformation("john@example.com");

        Patron patron2 = new Patron();
        patron2.setId(2L);
        patron2.setName("Jane");
        patron2.setContactInformation("jane@example.com");

        when(patronRepository.findAll()).thenReturn(Arrays.asList(patron1, patron2));


        List<PatronDTO> patronDTOs = patronService.getAllPatrons();


        assertEquals(2, patronDTOs.size());
        assertEquals("John", patronDTOs.get(0).getName());
        assertEquals("Jane", patronDTOs.get(1).getName());
    }

    @Test
    void getPatronById() {

        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("John");
        patron.setContactInformation("john@example.com");

        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));


        PatronDTO patronDTO = patronService.getPatronById(1L);


        assertEquals("John", patronDTO.getName());
        assertEquals("john@example.com", patronDTO.getContactInformation());
    }

    @Test
    void addPatron() {

        PatronDTO patronDTO = new PatronDTO();
        patronDTO.setName("John");
        patronDTO.setContactInformation("john@example.com");

        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("John");
        patron.setContactInformation("john@example.com");

        when(patronRepository.save(any(Patron.class))).thenReturn(patron);


        PatronDTO savedPatronDTO = patronService.addPatron(patronDTO);


        assertEquals("John", savedPatronDTO.getName());
        assertEquals("john@example.com", savedPatronDTO.getContactInformation());
    }

    @Test
    void updatePatron() {

        long id = 1L;
        PatronDTO updatedPatronDTO = new PatronDTO();
        updatedPatronDTO.setName("Updated Name");
        updatedPatronDTO.setContactInformation("updated@example.com");

        Patron existingPatron = new Patron();
        existingPatron.setId(id);
        existingPatron.setName("John");
        existingPatron.setContactInformation("john@example.com");

        when(patronRepository.findById(id)).thenReturn(Optional.of(existingPatron));
        when(patronRepository.save(any(Patron.class))).thenAnswer(invocation -> invocation.getArgument(0)); // Mock save to return the updated entity


        PatronDTO result = patronService.updatePatron(id, updatedPatronDTO);


        assertEquals("Updated Name", result.getName());
        assertEquals("updated@example.com", result.getContactInformation());
    }

    @Test
    void deletePatron() {

        long id = 1L;
        Patron existingPatron = new Patron();
        existingPatron.setId(id);
        existingPatron.setName("John");
        existingPatron.setContactInformation("john@example.com");

        when(patronRepository.findById(id)).thenReturn(Optional.of(existingPatron));


        patronService.deletePatron(id);


        verify(patronRepository, times(1)).delete(existingPatron);
    }


}