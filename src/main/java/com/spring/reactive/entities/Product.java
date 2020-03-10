package com.spring.reactive.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Product {

    private Integer bpnId;
    private String status;
    private Department department;
    private Asile aisle;
    private Shelf shelf;


}
