package com.techie.librarymanagementsystem.services.interfaceImpl;

import com.techie.librarymanagementsystem.dtos.BookDTO;
import com.techie.librarymanagementsystem.entities.Book;
import com.techie.librarymanagementsystem.exceptions.ResourceNotFoundException;
import com.techie.librarymanagementsystem.repositories.BookRepository;
import com.techie.librarymanagementsystem.services.serviceInterfaces.BookService;
import org.springframework.cache.annotation.Cacheable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Transactional(readOnly = true)
    @Cacheable("books")
    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Cacheable("books")
    @Override
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return convertToDTO(book);
    }

    @Transactional
    @Override
    public BookDTO addBook(BookDTO bookDTO) {
        Book book = convertToEntity(bookDTO);
        System.out.println("my book is saved.... " + book.getAuthor());
        book = bookRepository.save(book);
        return convertToDTO(book);
    }

    @Transactional
    @Override
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        existingBook.setTitle(bookDTO.getTitle());
        existingBook.setAuthor(bookDTO.getAuthor());
        existingBook.setPublicationYear(bookDTO.getPublicationYear());
        existingBook.setIsbn(bookDTO.getIsbn());
        existingBook = bookRepository.save(existingBook);
        return convertToDTO(existingBook);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        bookRepository.delete(book);
    }



    private BookDTO convertToDTO(Book book) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(book, BookDTO.class);
    }

    private Book convertToEntity(BookDTO bookDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(bookDTO, Book.class);
    }
}
