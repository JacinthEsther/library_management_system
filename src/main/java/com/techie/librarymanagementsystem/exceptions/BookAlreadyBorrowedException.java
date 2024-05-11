package com.techie.librarymanagementsystem.exceptions;

public class BookAlreadyBorrowedException extends RuntimeException {
    public BookAlreadyBorrowedException(String message) {
        super(message);
    }
}
