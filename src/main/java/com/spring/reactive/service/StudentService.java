package com.spring.reactive.service;


import com.spring.reactive.entities.Student;
import com.spring.reactive.exception.BadRequestException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentService {

    Mono<Student> addStudent(Student student);
    Mono<Void> deleteStudent(String studentId);
    Mono<Student> findStudent(String studentId) throws BadRequestException;
    Flux<Student> findAllStudent();
    Mono<Student> updateStudent(Student student);
    Mono<Student> findStudentByName(String studentName);
}
