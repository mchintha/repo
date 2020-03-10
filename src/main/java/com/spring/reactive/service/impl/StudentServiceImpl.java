package com.spring.reactive.service.impl;

import com.spring.reactive.entities.Student;
import com.spring.reactive.repository.StudentReactiveRepository;
import com.spring.reactive.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentReactiveRepository studentReactiveRepository;

    @Override
    public Mono<Student> addStudent(Student student) {

        return studentReactiveRepository.insert(student);
    }


    @Override
    public Mono<Void> deleteStudent(String student) {

        Mono<Void> voidMono = studentReactiveRepository.deleteById(student);
        return voidMono;
    }

    @Override
    public Mono<Student> findStudent(String studentId) {
        return studentReactiveRepository.findById(studentId);
    }

    @Override
    public Flux<Student> findAllStudent() {
       return studentReactiveRepository.findAll();
    }

    @Override
    public Mono<Student> updateStudent(Student student) {
        return studentReactiveRepository.save(student);
    }

    @Override
    public Mono<Student> findStudentByName(String studentName) {
      return studentReactiveRepository.findByQuery(studentName);
    }


}
