package com.spring.reactive.entities;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
@Setter
@Getter
public class Student {

    @Id
    private String studentId;
    private String studentName;
    private int age;
    private String email;
    private String dept;
    private Double salary;
}
