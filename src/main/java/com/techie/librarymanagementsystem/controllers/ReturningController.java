package com.techie.librarymanagementsystem.controllers;

import com.techie.librarymanagementsystem.services.serviceInterfaces.BorrowingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/return")
@Api(tags = "return book controller")
public class ReturningController {

    @Autowired
    private BorrowingService borrowingService;

    @PutMapping("/{bookId}/patron/{patronId}")
    @ApiOperation("return books")
    public ResponseEntity<Void> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        borrowingService.returnBook(bookId, patronId);
        return ResponseEntity.ok().build();
    }
}
