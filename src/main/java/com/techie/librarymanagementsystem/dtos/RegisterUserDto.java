package com.techie.librarymanagementsystem.dtos;


import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Validated
public class RegisterUserDto {
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
            message = "Invalid email format")
    private String email;

    @Size(min = 4, message = "Password should be at least 4 characters long")
    private String password;


}
