package com.techie.librarymanagementsystem.exceptions;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message;
    private int status;

}
