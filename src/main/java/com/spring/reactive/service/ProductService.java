package com.spring.reactive.service;

import com.spring.reactive.entities.Product;
import com.spring.reactive.entities.ProductUpc;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Mono<Product> createProduct(Product product);

    Mono<ProductUpc> createProductUpc(ProductUpc productUpc);

    Flux<ProductUpc> getProductUpc(Integer bpnId);

    Flux<Product> getProduct(String... filterTypeAndValue);

    Flux<ProductUpc> getProductUpcByRogId(String filterType,String bpnId);
}
