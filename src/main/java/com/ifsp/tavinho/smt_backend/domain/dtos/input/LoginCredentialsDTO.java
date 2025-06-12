package com.ifsp.tavinho.smt_backend.domain.dtos.input;

import jakarta.validation.constraints.NotBlank;

public record LoginCredentialsDTO(
    @NotBlank(message = "Email is required") String email, 
    @NotBlank(message = "Password is required") String password
) { }
