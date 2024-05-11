package com.techie.librarymanagementsystem.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PatronDTO {

    @NotBlank(message = "Name is required")
    private String name;

    private String contactInformation;
}
