package com.techie.librarymanagementsystem.services.interfaceImpl;

import com.techie.librarymanagementsystem.entities.Book;
import com.techie.librarymanagementsystem.entities.BorrowingRecord;
import com.techie.librarymanagementsystem.entities.Patron;
import com.techie.librarymanagementsystem.exceptions.BookAlreadyBorrowedException;
import com.techie.librarymanagementsystem.exceptions.ResourceNotFoundException;
import com.techie.librarymanagementsystem.repositories.BookRepository;
import com.techie.librarymanagementsystem.repositories.BorrowingRecordRepository;
import com.techie.librarymanagementsystem.repositories.PatronRepository;
import com.techie.librarymanagementsystem.services.serviceInterfaces.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BorrowingServiceImpl implements BorrowingService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PatronRepository patronRepository;

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Override
    public void borrowBook(Long bookId, Long patronId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new ResourceNotFoundException("Patron not found with id: " + patronId));

        if (book.isBorrowed()) {
            throw new BookAlreadyBorrowedException("Book with id " + bookId + " is already borrowed");
        }

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDate.now());

        borrowingRecordRepository.save(borrowingRecord);

        book.setBorrowed(true);
        bookRepository.save(book);
    }

    @Override
    public void returnBook(Long bookId, Long patronId) {
        BorrowingRecord borrowingRecord = borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId)
                .orElseThrow(() -> new ResourceNotFoundException("No active borrowing record found for book id: " + bookId + " and patron id: " + patronId));

        borrowingRecord.setReturnDate(LocalDate.now());
        borrowingRecordRepository.save(borrowingRecord);

        Book book = borrowingRecord.getBook();
        book.setBorrowed(false);
        bookRepository.save(book);
    }
}
