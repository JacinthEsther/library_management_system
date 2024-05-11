package com.techie.librarymanagementsystem.controllers;

import com.techie.librarymanagementsystem.dtos.BookDTO;
import com.techie.librarymanagementsystem.exceptions.ErrorResponse;
import com.techie.librarymanagementsystem.exceptions.ResourceNotFoundException;
import com.techie.librarymanagementsystem.services.serviceInterfaces.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@Api(tags = "Book Management in the library management system")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("")
    @ApiOperation("Get all books")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    @ApiOperation("Get book by id")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping("addBooks")
    @ApiOperation("add books")
    public ResponseEntity<BookDTO> addBook(@Valid @RequestBody BookDTO bookDTO) {
        BookDTO savedBook = bookService.addBook(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @PutMapping("/{id}")
    @ApiOperation("update book")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO) {
        BookDTO updatedBook = bookService.updateBook(id, bookDTO);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("delete book")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
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
