package com.techie.librarymanagementsystem.controllers;

import com.techie.librarymanagementsystem.dtos.PatronDTO;
import com.techie.librarymanagementsystem.exceptions.ErrorResponse;
import com.techie.librarymanagementsystem.exceptions.ResourceNotFoundException;
import com.techie.librarymanagementsystem.services.serviceInterfaces.PatronService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/patrons")
@Api(tags = "Patron Management")
public class PatronController {

    @Autowired
    private PatronService patronService;

    @GetMapping
    @ApiOperation("Get all patrons")
    public ResponseEntity<List<PatronDTO>> getAllPatrons() {
        List<PatronDTO> patrons = patronService.getAllPatrons();
        return ResponseEntity.ok(patrons);
    }

    @GetMapping("/{id}")
    @ApiOperation("Get patron by id")
    public ResponseEntity<PatronDTO> getPatronById(@PathVariable Long id) {
        PatronDTO patron = patronService.getPatronById(id);
        return ResponseEntity.ok(patron);
    }

    @PostMapping
    @ApiOperation("add patron")
    public ResponseEntity<PatronDTO> addPatron(@Valid @RequestBody PatronDTO patronDTO) {
        PatronDTO savedPatron = patronService.addPatron(patronDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPatron);
    }

    @PutMapping("/{id}")
    @ApiOperation("update patron")
    public ResponseEntity<PatronDTO> updatePatron(@PathVariable Long id, @Valid @RequestBody PatronDTO patronDTO) {
        PatronDTO updatedPatron = patronService.updatePatron(id, patronDTO);
        return ResponseEntity.ok(updatedPatron);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("delete patron")
    public ResponseEntity<Void> deletePatron(@PathVariable Long id) {
        patronService.deletePatron(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
