package com.techie.librarymanagementsystem.services.serviceInterfaces;

import com.techie.librarymanagementsystem.dtos.BookDTO;

import java.util.List;

public interface BookService {
    List<BookDTO> getAllBooks();
    BookDTO getBookById(Long id);
    BookDTO addBook(BookDTO bookDTO);
    BookDTO updateBook(Long id, BookDTO bookDTO);
    void deleteBook(Long id);
}
