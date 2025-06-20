package com.ifsp.tavinho.smt_backend.application.services.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.ClassroomDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Classroom;
import com.ifsp.tavinho.smt_backend.domain.usecases.classroom.CreateClassroomUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.classroom.UpdateClassroomUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.classroom.DeleteClassroomUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.classroom.FindClassroomUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.classroom.ListClassroomsUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.event.IsEntityLinkedToEventUseCase;
import com.ifsp.tavinho.smt_backend.shared.errors.AppError;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClassroomService {

    private final CreateClassroomUseCase createClassroom;
    private final UpdateClassroomUseCase updateClassroom;
    private final DeleteClassroomUseCase deleteClassroom;
    private final FindClassroomUseCase findClassroom;
    private final ListClassroomsUseCase listClassrooms;

    private final IsEntityLinkedToEventUseCase isEntityLinkedToEvent;

    public Classroom create(ClassroomDTO input) {
        return this.createClassroom.execute(input);
    }

    public Classroom update(ClassroomDTO input, String id) {
        Classroom classroom = this.findClassroom.execute(id);
        return this.updateClassroom.execute(input, classroom);
    }

    public Boolean delete(String id) {
        Classroom classroom = this.findClassroom.execute(id);

        if (this.isEntityLinkedToEvent.execute(classroom)) {
            throw new AppError("Classroom could not be deleted because it is linked to an event.", HttpStatus.CONFLICT);
        }

        return this.deleteClassroom.execute(classroom);
    }

    public Classroom find(String id) {
        return this.findClassroom.execute(id);
    }

    public List<Classroom> list() {
        return this.listClassrooms.execute(null);
    }
    
} 