package com.ifsp.tavinho.smt_backend.domain.usecases.event;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.EventDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Event;
import com.ifsp.tavinho.smt_backend.domain.repositories.ClassroomRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.CourseRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.DisciplineRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.ProfessorRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CreateEventUseCase implements UseCase<EventDTO, Event> {

    private final EventRepository eventRepository;
    private final ClassroomRepository classroomRepository;
    private final ProfessorRepository professorRepository;
    private final CourseRepository courseRepository;
    private final DisciplineRepository disciplineRepository;

    @Override
    public ResponseEntity<Event> execute(EventDTO input) {
        this.classroomRepository.findById(input.classroomId()).orElseThrow(() -> new EntityNotFoundException("Classroom not found with id: " + input.classroomId()));
        this.professorRepository.findById(input.professorId()).orElseThrow(() -> new EntityNotFoundException("Professor not found with id: " + input.professorId()));
        this.courseRepository.findById(input.courseId()).orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + input.courseId()));
        this.disciplineRepository.findById(input.disciplineId()).orElseThrow(() -> new EntityNotFoundException("Discipline not found with id: " + input.disciplineId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(
            this.eventRepository.save(
                new Event(
                    input.description(), 
                    input.weekday(), 
                    input.startTime(), 
                    input.endTime(), 
                    input.classroomId(),
                    input.professorId(),
                    input.courseId(),
                    input.disciplineId()
                )
            )
        );
    }
    
}
