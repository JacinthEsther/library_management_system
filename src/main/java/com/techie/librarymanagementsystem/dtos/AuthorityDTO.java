package com.techie.librarymanagementsystem.dtos;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class AuthorityDTO {
    private Long userId;

    @Pattern(regexp = "^(?i)(user|admin|superUser)$", message = "Role should be 'user' or 'admin' or 'superUser")
    private String role;
}
