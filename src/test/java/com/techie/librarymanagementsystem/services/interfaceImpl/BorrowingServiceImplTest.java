package com.techie.librarymanagementsystem.services.interfaceImpl;

import com.techie.librarymanagementsystem.entities.Book;
import com.techie.librarymanagementsystem.entities.BorrowingRecord;
import com.techie.librarymanagementsystem.entities.Patron;
import com.techie.librarymanagementsystem.exceptions.BookAlreadyBorrowedException;
import com.techie.librarymanagementsystem.exceptions.ResourceNotFoundException;
import com.techie.librarymanagementsystem.repositories.BookRepository;
import com.techie.librarymanagementsystem.repositories.BorrowingRecordRepository;
import com.techie.librarymanagementsystem.repositories.PatronRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class BorrowingServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PatronRepository patronRepository;

    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;


    @InjectMocks
    private BorrowingServiceImpl borrowingService;

    @Test
    void borrowBook() {

        long bookId = 1L;
        long patronId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setBorrowed(false);

        Patron patron = new Patron();
        patron.setId(patronId);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));
        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(new BorrowingRecord());


        borrowingService.borrowBook(bookId, patronId);


        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void returnBook() {

        long bookId = 1L;
        long patronId = 1L;
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(new Book());
        borrowingRecord.setPatron(new Patron());

        when(borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId)).thenReturn(Optional.of(borrowingRecord));


        borrowingService.returnBook(bookId, patronId);


        verify(borrowingRecordRepository, times(1)).save(borrowingRecord);
    }

    @Test
    void borrowBook_whenBookAlreadyBorrowed_shouldThrowException() {

        long bookId = 1L;
        long patronId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setBorrowed(true);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));


        assertThrows(BookAlreadyBorrowedException.class, () -> borrowingService.borrowBook(bookId, patronId));
    }

    @Test
    void returnBook_whenNoActiveBorrowingRecord_shouldThrowException() {

        long bookId = 1L;
        long patronId = 1L;

        when(borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> borrowingService.returnBook(bookId, patronId));
    }


}