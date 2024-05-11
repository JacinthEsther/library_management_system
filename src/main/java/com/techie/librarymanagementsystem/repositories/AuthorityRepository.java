package com.techie.librarymanagementsystem.repositories;

import com.techie.librarymanagementsystem.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
