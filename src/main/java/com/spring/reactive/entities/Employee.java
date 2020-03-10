package com.spring.reactive.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employee")
@Setter
@Getter
public class Employee {

    @Id
    private Integer id;
    private String employeeName;
    private Integer age;
    private String email;
}
