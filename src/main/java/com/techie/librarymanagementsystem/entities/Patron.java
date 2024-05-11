package com.techie.librarymanagementsystem.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Patron {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patron_id")
    private Long id;

    private String name;
    private String contactInformation;
    @OneToMany(mappedBy = "patron", cascade = CascadeType.ALL)
    private List<BorrowingRecord> borrowingRecords = new ArrayList<>();
}
