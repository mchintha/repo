package com.spring.reactive.model;

import com.spring.reactive.entities.Product;
import com.spring.reactive.entities.ProductUpc;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
public class ProductResponse {

    private Product product;
    private List<ProductUpc> productUpcList;

    public ProductResponse(){}
}
