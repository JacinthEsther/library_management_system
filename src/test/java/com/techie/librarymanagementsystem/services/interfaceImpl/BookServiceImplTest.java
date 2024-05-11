package com.techie.librarymanagementsystem.services.interfaceImpl;

import com.techie.librarymanagementsystem.dtos.BookDTO;
import com.techie.librarymanagementsystem.entities.Book;
import com.techie.librarymanagementsystem.repositories.BookRepository;
import com.techie.librarymanagementsystem.services.serviceInterfaces.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookServiceImplTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @Test
    void addBook() {

        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Title");
        bookDTO.setAuthor("Author");
        bookDTO.setIsbn("qwerty1234");
        bookDTO.setPublicationYear(2003);


        BookDTO result = bookService.addBook(bookDTO);



        assertEquals("Title", result.getTitle());
    }

    @Test
    void getAllBooks() {
        List<Book> books = Arrays.asList(new Book(), new Book());
        when(bookRepository.findAll()).thenReturn(books);


        List<BookDTO> result = bookService.getAllBooks();


        assertEquals(2, result.size());
    }

    @Test
    void getBookById() {
        Long bookId = 1L;
        String bookTitle = "title";
        Book book = new Book();
        book.setId(bookId);
        book.setTitle(bookTitle);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));


        BookDTO result = bookService.getBookById(bookId);


        assertNotNull(result);
        assertEquals(bookTitle, result.getTitle());
    }

    @Test
    void updateBook() {
        Long bookId = 1L;
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Updated Title");


        BookDTO result = bookService.updateBook(bookId, bookDTO);


        assertNotNull(result);
    }

    @Test
    void deleteBook() {

        Long bookId = 1L;


        assertDoesNotThrow(() -> bookService.deleteBook(bookId));


        verify(bookRepository, Mockito.times(1)).deleteById(bookId);
    }
}