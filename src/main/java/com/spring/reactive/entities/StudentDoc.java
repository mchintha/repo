package com.spring.reactive.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Setter
@Getter
public class StudentDoc {

    @Id
    private Integer id;
    private String studentName;
    private Integer age;
    private String email;
    private String street;
}
