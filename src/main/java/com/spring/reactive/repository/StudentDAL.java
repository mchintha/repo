package com.spring.reactive.repository;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.spring.reactive.entities.Employee;
import com.spring.reactive.entities.Student;
import com.spring.reactive.entities.StudentDoc;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface StudentDAL {

    List<Student> getAllStudents();

    Mono<Student> getStudentById(String userId);

    Mono<Student> addNewStudent(Student user);

    Mono<Student> updateStudent(Student student);

    Mono<DeleteResult> deleteStudent(Student studentId);

    Mono<Student> studentByIdAndName(String studentId, String studentName);

    Mono<Boolean> studentsByName(String studentName);

    Flux<Student> studentsStartWithA();

    Flux<Student> studentsWithAge();

    public Flux<Student> studentsWithAgeSortingOrder();

    public Flux<StudentDoc> addNewStudentDoc(List<StudentDoc> studentDoc);

    public Flux<Employee> addNewEmployee(List<Employee> employee);

    public Flux<StudentDoc> studentDocStartWith(String startWith);

    public Mono<StudentDoc> updateStudentDoc(StudentDoc student);

    public Mono<UpdateResult> updateEmplyee(Integer id,String value);

    public Mono<Employee> getEmployee(Integer id);

    public Mono<UpdateResult> updateEmp(List<Employee> emp);

    public Mono<UpdateResult> updateStudent1(Student studentId);

    public Mono<UpdateResult> updateEmpp(Employee employee);

    public Mono<Boolean> checkCollectionExists(Class student);

    public Mono<UpdateResult> upsertExample(Student student);
}
