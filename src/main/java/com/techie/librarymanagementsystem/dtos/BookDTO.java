package com.techie.librarymanagementsystem.dtos;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class BookDTO {
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Author is required")
    private String author;
    @Min(value = 1000, message = "Publication year must be at least 1000")
    private int publicationYear;
    @NotBlank(message = "ISBN is required")
    private String isbn;
}
