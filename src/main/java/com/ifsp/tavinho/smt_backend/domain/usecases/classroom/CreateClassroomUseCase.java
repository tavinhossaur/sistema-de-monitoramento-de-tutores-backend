package com.ifsp.tavinho.smt_backend.domain.usecases.classroom;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.ClassroomDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Classroom;
import com.ifsp.tavinho.smt_backend.domain.repositories.ClassroomRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateClassroomUseCase implements UseCase<ClassroomDTO, Classroom> {

    private final ClassroomRepository repository;

    @Override
    public Classroom execute(ClassroomDTO input) {
        return this.repository.save(
            new Classroom(
                input.description(),
                input.block(),
                input.floor(),
                input.capacity(),
                input.observation()
            )
        );
    }
    
}
