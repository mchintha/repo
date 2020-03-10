package com.spring.reactive.service.impl;

import com.spring.reactive.entities.Product;
import com.spring.reactive.entities.ProductUpc;
import com.spring.reactive.repository.ProductRepository;
import com.spring.reactive.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductRepository productRepository;

    @Override
    public Mono<Product> createProduct(Product product) {
        return productRepository.createProduct(product);
    }

    @Override
    public Mono<ProductUpc> createProductUpc(ProductUpc productUpc) {
        return productRepository.createProductUpc(productUpc);
    }

    @Override
    public Flux<ProductUpc> getProductUpc(Integer bpnId) {
        return productRepository.getProductUpc(bpnId);
    }

    @Override
    public Flux<Product> getProduct(String... filterTypeAndValue) {
        String[] clone = filterTypeAndValue.clone();
        if(clone.length != 0) {
            return productRepository.getProduct(clone[0], clone[1]);
        }
        return productRepository.getProduct();
    }

    @Override
    public Flux<ProductUpc> getProductUpcByRogId(String filterType, String bpnId) {
        return productRepository.getProductUpcByRockId(filterType,bpnId);
    }
}
