package com.ifsp.tavinho.smt_backend.domain.usecases.user.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.UserDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;
import com.ifsp.tavinho.smt_backend.infra.services.AuthenticationService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CreateUserUseCase implements UseCase<UserDTO, User> {

    private final AuthenticationService authenticationService;

    @Override
    public ResponseEntity<User> execute(UserDTO credentials) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.register(credentials));
    }
    
}
