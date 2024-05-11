package com.techie.librarymanagementsystem.repositories;

import com.techie.librarymanagementsystem.entities.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronRepository extends JpaRepository<Patron, Long> {
}
