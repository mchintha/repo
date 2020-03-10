package com.spring.reactive.repository.impl;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.spring.reactive.entities.Employee;
import com.spring.reactive.entities.Student;
import com.spring.reactive.entities.StudentDoc;
import com.spring.reactive.repository.StudentDAL;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentDALImpl implements StudentDAL {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public List<Student> getAllStudents() {
        return mongoTemplate.findAll(Student.class);
    }

    @Override
    public Mono<Student> getStudentById(String studentId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("studentId").is(studentId));
        return reactiveMongoTemplate.findOne(query,Student.class);
    }

    @Override
    public Mono<Student> addNewStudent(Student user) {
        return reactiveMongoTemplate.insert(user);
    }

    @Override
    public Mono<Student> updateStudent(Student student) {

        return reactiveMongoTemplate.save(Mono.just(student));
    }

    @Override
    public Mono<DeleteResult> deleteStudent(Student studentId) {
        Mono<DeleteResult> remove = reactiveMongoTemplate.remove(studentId);
        return remove;
    }

    @Override
    public Mono<Student> studentByIdAndName(String studentId, String studentName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("studentId").is(studentId).and("studentName").is(studentName));
        return reactiveMongoTemplate.findOne(query,Student.class);
    }

    @Override
    public Mono<Boolean> studentsByName(String studentName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("studentName").regex("^"+studentName+"$","i"));
        return reactiveMongoTemplate.exists(query,Student.class);
    }

    public Mono<Student> studentsByNm(String studentName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("studentName").regex("\\G\\w+","i"));
        return reactiveMongoTemplate.findOne(query,Student.class);
    }

    @Override
    public Flux<Student> studentsStartWithA() {
        Query query = new Query();
        query.addCriteria(Criteria.where("studentName").regex("^A"));
        return reactiveMongoTemplate.find(query,Student.class);
    }


    @Override
    public Flux<Student> studentsWithAge() {
        Query  query = new Query();
        query.addCriteria(Criteria.where("age").lt("30").gt("25"));
        return reactiveMongoTemplate.find(query,Student.class);
    }

    @Override
    public Flux<Student> studentsWithAgeSortingOrder() {
        Sort sort = Sort.by(Sort.Direction.DESC,"age");
        Query query = new Query();
        query.with(sort);
        return reactiveMongoTemplate.find(query,Student.class);
    }

    @Override
    public Flux<StudentDoc> addNewStudentDoc(List<StudentDoc> studentDocList) {
        return reactiveMongoTemplate.insertAll(studentDocList);
    }

    @Override
    public Flux<Employee> addNewEmployee(List<Employee> employee) {
        return reactiveMongoTemplate.insertAll(employee);
    }

    @Override
    public Flux<StudentDoc> studentDocStartWith(String startWith) {
        Query query = new Query();
        query.addCriteria(Criteria.where("studentName").regex("^"+startWith+""));
        return reactiveMongoTemplate.find(query,StudentDoc.class);
    }

    @Override
    public Mono<StudentDoc> updateStudentDoc(StudentDoc student) {
        return reactiveMongoTemplate.save(student);
    }

    @Override
    public Mono<UpdateResult> updateEmplyee(Integer id,String value) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("street",value);
        return reactiveMongoTemplate.updateMulti(query,update,Employee.class);
    }

    @Override
    public Mono<Employee> getEmployee(Integer id) {
        return reactiveMongoTemplate.findById(id,Employee.class);
    }


    @Override
    public Mono<UpdateResult> updateEmp(List<Employee> emp) {

        Query query = new Query();
        query.addCriteria(Criteria.where("employeeName").is("bbc"));
        Update update = new Update();
        for(Employee e : emp) {
            update.set("employeeName", "abc");
        }
        return reactiveMongoTemplate.updateFirst(query, update, Employee.class,"employee");
    }

    @Override
    public Mono<UpdateResult> updateStudent1(Student studentId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("studentId").is(studentId.getStudentId()));
        Update update = new Update();
        update.set("studentName",studentId.getStudentName());
        update.set("age",studentId.getAge());
        return reactiveMongoTemplate.updateFirst(query,update,"student");
    }

    @Override
    public Mono<UpdateResult> updateEmpp(Employee employee) {

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(employee.getId()));
        Update update = new Update();
        update.set("employeeName",employee.getEmployeeName());
        return reactiveMongoTemplate.updateFirst(query,update,"employee");
    }

    @Override
    public Mono<Boolean> checkCollectionExists(Class student) {
        Mono<Boolean> mono = reactiveMongoTemplate.collectionExists(student);
        return mono;
    }

    @Override
    public Mono<UpdateResult> upsertExample(Student student) {
        Query query = new Query();

        query.addCriteria(Criteria.where("studentName").is(student.getStudentName())
                .andOperator(Criteria.where("age").is(student.getAge())));

        Update update = new Update();
        update.set("email",student.getEmail());

        return reactiveMongoTemplate.upsert(query,update,"student");
    }

    public Page<Student> getPaginationResult() {

        PageRequest age = PageRequest.of(1, 3, Sort.Direction.DESC, "age");
        Query  query = new Query();
        query.with(age);

        List<Student> students = mongoTemplate.find(query, Student.class);

        Page<Student> page = PageableExecutionUtils.getPage(students, age,
                () -> mongoTemplate.count(query, Student.class));

        return page;
    }

    public Flux<Student> getAggrResult() {

        MatchOperation age = Aggregation.match(new Criteria("studentName").is("testapp"));
        //SortOperation age1 = Aggregation.sort(Sort.Direction.DESC, "age");

        GroupOperation dept = Aggregation.group("dept").sum("salary").as("salary");

        Aggregation aggregation = Aggregation.newAggregation(age,dept);

        Flux<Student> student = reactiveMongoTemplate.aggregate(aggregation, "student", Student.class);
        return student;
    }
}
