package com.spring.reactive.controller;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.spring.reactive.entities.Employee;
import com.spring.reactive.entities.Student;
import com.spring.reactive.entities.StudentDoc;
import com.spring.reactive.model.StudentDTO;
import com.spring.reactive.repository.impl.StudentDALImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/studentquery")
public class StudentQueryController {


    @Autowired
    private StudentDALImpl studentDAL;


    @GetMapping("/find/{id}")
    public Mono<Student> findStudent(@PathVariable("id") String studentId) {
        return studentDAL.getStudentById(studentId);
    }

    @PostMapping("/create")
    public Mono<ServerResponse> addStudent(@RequestBody StudentDTO studentDTO) {
        Student student = new Student();
        BeanUtils.copyProperties(studentDTO, student);
        return studentDAL.addNewStudent(student).flatMap(s -> ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON).syncBody(s))
                .onErrorResume(e -> Mono.just("Error "+ e.getMessage())
                        .flatMap(s -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).syncBody(s)));
    }

    @PutMapping("/update")
    public Mono<ResponseEntity<Student>> updateStudent(@RequestBody StudentDTO studentDTO) {
        Mono<Student> studentById = studentDAL.getStudentById(studentDTO.getStudentId());
        Mono<Student> objectMono = studentById.flatMap(student -> {
            BeanUtils.copyProperties(studentDTO, student);
            return studentDAL.updateStudent(student);
        });

        return objectMono.map(student -> new ResponseEntity<Student>(student, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete")
    public Mono<DeleteResult> deleteStudent(@RequestBody Student studentId) {
        return studentDAL.getStudentById(studentId.getStudentId())
                .flatMap(student ->
                        studentDAL.deleteStudent(studentId));
    }

    @GetMapping("/student/{studentId}/{studentName}")
    public Mono<ResponseEntity<Student>> getStudent(@PathVariable("studentId") String studentId, @PathVariable("studentName") String studentName) {
        Mono<Student> studentMono = studentDAL.studentByIdAndName(studentId, studentName);

        return studentMono.map(student -> new ResponseEntity<Student>(student, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    /*
    Getting Flux of students by passing studentName
     */
    @GetMapping("/student/{studentName}")
    public Mono<Boolean> getStudentsByName(@PathVariable("studentName") String studentName) {
        return studentDAL.studentsByName(studentName);
    }

    @GetMapping("/student1/{studentNm}")
    public Mono<Student> getStudentByName(@PathVariable("studentNm") String studentName) {
        return studentDAL.studentsByNm(studentName);
    }

    /*
    Getting Flux of students whose name start with A
     */

    @GetMapping("/student/startingwithA")
    public Flux<Student> studentsStartingWithA() {
        return studentDAL.studentsStartWithA();
    }

    /*
    Getting Flux of students whose age in between 25 and 30
     */

    @GetMapping("/student/studentwithage")
    public Flux<Student> studentWithAge() {
        return studentDAL.studentsWithAge();
    }

    /*
    Getting Flux of students by asc age sorting order
     */
    public Flux<Student> studentsWithAgeSortingOrder() {
        return studentDAL.studentsWithAgeSortingOrder();
    }

    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public Flux<StudentDoc> addStudentDoc(@RequestBody List<StudentDoc> students) {
        return studentDAL.addNewStudentDoc(students);
    }

    @PostMapping("/insertemp")
    @ResponseStatus(HttpStatus.CREATED)
    public Flux<Employee> addEmployee(@RequestBody List<Employee> employee) {
        return studentDAL.addNewEmployee(employee);
    }


    @PutMapping("/updatedoc/{startWith}/{valueToBeSet}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<UpdateResult> updateStudentAndEmp(@PathVariable("startWith") String startWith, @PathVariable("valueToBeSet") String valueToBeSet) {
        Flux<StudentDoc> studentDocFlux = studentDAL.studentDocStartWith(startWith);
        Flux<StudentDoc> studentDocFlux1 = studentDocFlux.flatMap(studentDoc -> {
            studentDoc.setStreet(valueToBeSet);
            return studentDAL.updateStudentDoc(studentDoc);
        });

        Flux<Employee> employeeFlux = studentDocFlux1.flatMap(studentDoc -> {
            return studentDAL.getEmployee(studentDoc.getId());
        });

        Flux<UpdateResult> updateResultFlux = employeeFlux.flatMap(employee -> {
            return studentDAL.updateEmplyee(employee.getId(), valueToBeSet);
        });

       return updateResultFlux;
    }

    @PutMapping("/updateemp")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UpdateResult> updateEmp(@RequestBody List<Employee> emp) {
        return studentDAL.updateEmp(emp);
    }


    @PutMapping("/updatestu")
    public Mono<UpdateResult> updateStudent(@RequestBody Student student) {
        return studentDAL.updateStudent1(student);
    }

    @PutMapping("/updemp")
    public Mono<UpdateResult> updateEmp(@RequestBody Employee employee) {
        return studentDAL.updateEmpp(employee);
    }


    @GetMapping("/checkcoll")
    public Mono<String> checkCollectionExists() {
        Mono<Boolean> booleanMono = studentDAL.checkCollectionExists(Student.class);
        Mono<String> map = booleanMono.map(flg -> {
            if (flg) {
                return "trueerwwr";
            } else {
                return "false";
            }
        });
        return map;
    }

    @PutMapping("/upsert")
    public Mono<UpdateResult> checkUpsert(@RequestBody Student student) {
        return studentDAL.upsertExample(student);
    }


    @GetMapping("/page")
    public Page<Student> getPagebleResult() {

        return studentDAL.getPaginationResult();
    }

    @GetMapping("/aggr")
    public Flux<Student> getAggrResult() {

        return studentDAL.getAggrResult();
    }
 }