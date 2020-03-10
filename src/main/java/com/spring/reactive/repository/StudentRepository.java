package com.spring.reactive.repository;

import com.spring.reactive.entities.Student;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface StudentRepository extends MongoRepository<Student, String> {

    @Query("{studentId:'?0'}")
    Student findStudent(String studentId);
}
