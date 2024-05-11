package com.techie.librarymanagementsystem.controllers;

import com.techie.librarymanagementsystem.services.serviceInterfaces.BorrowingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/borrow")
@Api(tags = "Borrowing books controller that shows number of books borrowed")
public class BorrowingController {

    @Autowired
    private BorrowingService borrowingService;

    @PostMapping("/{bookId}/patron/{patronId}")
    @ApiOperation("borrow book")
    public ResponseEntity<Void> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        borrowingService.borrowBook(bookId, patronId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
