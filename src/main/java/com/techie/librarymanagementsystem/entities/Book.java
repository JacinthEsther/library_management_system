package com.techie.librarymanagementsystem.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    private String title;
    private String author;
    private int publicationYear;
    private String isbn;
    private boolean borrowed;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BorrowingRecord> borrowingRecords = new ArrayList<>();
}
