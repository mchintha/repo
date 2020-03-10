package com.spring.reactive.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class ProductUpc {

    private Integer bpnId;
    private String rogcd;
    private Integer upcId;
    private String productGroup;
    private String prodcutClass;
    private String productSubClassLevel1;
    private String productSubClassLevel2;

}
