package com.ifsp.tavinho.smt_backend.domain.usecases.user;

import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.LoginCredentialsDTO;
import com.ifsp.tavinho.smt_backend.domain.dtos.output.LoginResponseDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.domain.enums.Status;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;
import com.ifsp.tavinho.smt_backend.infra.services.AuthenticationService;
import com.ifsp.tavinho.smt_backend.infra.services.JwtService;
import com.ifsp.tavinho.smt_backend.shared.responses.ApiResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoginUseCase implements UseCase<LoginCredentialsDTO, ApiResponse<LoginResponseDTO>> {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @Override
    public ResponseEntity<ApiResponse<LoginResponseDTO>> execute(LoginCredentialsDTO credentials) { 
        User authenticatedUser = authenticationService.login(credentials);
        return ResponseEntity.ok(
            ApiResponse.<LoginResponseDTO>builder()
                .status(Status.SUCCESS)
                .message("User logged in successfully.")
                .data(this.createAuthenticationResponse(authenticatedUser)
            ).build()
        );
    }

    private LoginResponseDTO createAuthenticationResponse(User user) {
        return new LoginResponseDTO(
            user.getId(),
            user.getFullName(),
            user.getEmail(),
            user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()),
            this.jwtService.generateToken(user), 
            this.jwtService.getExpirationTime()
        );
    }

}
