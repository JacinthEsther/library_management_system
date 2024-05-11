package com.techie.librarymanagementsystem.repositories;

import com.techie.librarymanagementsystem.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
