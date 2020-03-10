package com.spring.reactive.repository.impl;

import com.spring.reactive.entities.Product;
import com.spring.reactive.entities.ProductUpc;
import com.spring.reactive.entities.Student;
import com.spring.reactive.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ProductRepositoryImpl implements ProductRepository {


    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public Mono<Product> createProduct(Product product) {
        return reactiveMongoTemplate.insert(product);
    }

    @Override
    public Mono<ProductUpc> createProductUpc(ProductUpc productUpc) {
        return reactiveMongoTemplate.insert(productUpc);
    }

    @Override
    public Flux<ProductUpc> getProductUpc(Integer bpnId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("bpnId").is(bpnId));
        return reactiveMongoTemplate.find(query,ProductUpc.class);
    }

    @Override
    public Flux<ProductUpc> getProductUpcByRockId(String filterType, String value) {
        Query query = new Query();
        query.addCriteria(Criteria.where(filterType).is(value));
        return reactiveMongoTemplate.find(query,ProductUpc.class);
    }

    @Override
    public Flux<Product> getProduct(String... filterTypeAndValue) {
        Query query = new Query();
        String[] clone = filterTypeAndValue.clone();
        if(clone.length != 0) {
            if (clone[0].equals("bpnId")) {
                query.addCriteria(Criteria.where("bpnId").is(Integer.valueOf(clone[1])));
                return reactiveMongoTemplate.find(query, Product.class);
            } else {
                query.addCriteria(Criteria.where(clone[0] + ".name").is(clone[1]));
                return reactiveMongoTemplate.find(query, Product.class);
            }
        } else {
            return reactiveMongoTemplate.findAll(Product.class);
        }

    }
}
