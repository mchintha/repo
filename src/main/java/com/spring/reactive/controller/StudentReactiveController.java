package com.spring.reactive.controller;

import com.spring.reactive.entities.Student;
import com.spring.reactive.exception.BadRequestExceptionFlux;
import com.spring.reactive.model.StudentDTO;
import com.spring.reactive.service.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/student")
public class StudentReactiveController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private Util util;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Student> addStudent(@RequestBody StudentDTO studentDTO) {
        Student student = new Student();
        BeanUtils.copyProperties(studentDTO,student);
        return studentService.addStudent(student);
    }

    @PutMapping("/update")
    public Mono<ResponseEntity<Student>> updateStudent(@RequestBody StudentDTO studentDTO) {
        return studentService.findStudent(studentDTO.getStudentId())
                .flatMap(beforeStudentDetails -> {
                    BeanUtils.copyProperties(studentDTO,beforeStudentDetails);
                    return studentService.updateStudent(beforeStudentDetails);
                }).map(updateStudent -> new ResponseEntity<Student>(updateStudent,HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<Student>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{studentId}")
    public Mono<ResponseEntity<Void>> deleteStudent(@PathVariable("studentId") String studentId) {
                       return studentService.deleteStudent(studentId)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)));
    }

    @GetMapping("/student/{studentId}")
    public Mono<ResponseEntity<Student>> getStudent(@PathVariable("studentId") String studentId) {
        return studentService.findStudent(studentId)
                .map(student -> new ResponseEntity<Student>(student,HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/students")
    public Flux<Student> getStudents() {
        return studentService.findAllStudent();
    }


    @GetMapping("/order/{type}/{id}")
    public Mono<ResponseEntity<Void>> order(@PathVariable("type") String type, @PathVariable("id") String id) {
        Pattern pattern = util.getAllPatterns().get(type);
        if(!pattern.matcher(id).matches()) {
            throw new BadRequestExceptionFlux(HttpStatus.BAD_REQUEST,"The "+type+" id is not valid");
        }
        return null;
    }

    @GetMapping("/orders/{type}/{id}")
    public Mono<ResponseEntity<Void>> orders(@PathVariable("type") String type, @PathVariable("id") String id) {
        Pattern pattern = util.getAllPatterns().get(type);
        if(!pattern.matcher(id).matches()) {
            throw new BadRequestExceptionFlux(HttpStatus.BAD_REQUEST,"The "+type+" id is not valid");
        }
        return Mono.just(new ResponseEntity<>(HttpStatus.OK));
    }


    @GetMapping("/find1/{studentName}")
    public Mono<Student> findStudentByName(@PathVariable("studentName") String studentName) {
        return studentService.findStudentByName(studentName);
    }

}
