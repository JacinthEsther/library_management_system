package com.techie.librarymanagementsystem.services.serviceInterfaces;

public interface BorrowingService {
    void borrowBook(Long bookId, Long patronId);
    void returnBook(Long bookId, Long patronId);
}