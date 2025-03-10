package com.techie.librarymanagementsystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ApiResponse {
    private boolean isSuccessful;
    private String message;
}
