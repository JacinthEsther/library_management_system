package com.techie.librarymanagementsystem.services.interfaceImpl;

import com.techie.librarymanagementsystem.dtos.PatronDTO;
import com.techie.librarymanagementsystem.entities.Patron;
import com.techie.librarymanagementsystem.exceptions.ResourceNotFoundException;
import com.techie.librarymanagementsystem.repositories.PatronRepository;
import com.techie.librarymanagementsystem.services.serviceInterfaces.PatronService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatronServiceImpl implements PatronService {
    @Autowired
    private PatronRepository patronRepository;

    @Transactional(readOnly = true)
    @Override
    public List<PatronDTO> getAllPatrons() {
        List<Patron> patrons = patronRepository.findAll();
        return patrons.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public PatronDTO getPatronById(Long id) {
        Patron patron = patronRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patron not found with id: " + id));
        return convertToDTO(patron);
    }

    @Transactional
    @Override
    public PatronDTO addPatron(PatronDTO patronDTO) {
        Patron patron = convertToEntity(patronDTO);
        patron = patronRepository.save(patron);
        return convertToDTO(patron);
    }

    @Transactional
    @Override
    public PatronDTO updatePatron(Long id, PatronDTO patronDTO) {
        Patron existingPatron = patronRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patron not found with id: " + id));
        existingPatron.setName(patronDTO.getName());
        existingPatron.setContactInformation(patronDTO.getContactInformation());
        existingPatron = patronRepository.save(existingPatron);
        return convertToDTO(existingPatron);
    }

    @Override
    public void deletePatron(Long id) {
        Patron patron = patronRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patron not found with id: " + id));
        patronRepository.delete(patron);
    }

    private PatronDTO convertToDTO(Patron patron) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(patron, PatronDTO.class);
    }

    private Patron convertToEntity(PatronDTO patronDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(patronDTO, Patron.class);
    }
}
