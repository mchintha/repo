package com.spring.reactive.repository;

import com.spring.reactive.entities.Product;
import com.spring.reactive.entities.ProductUpc;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {

    Mono<Product> createProduct(Product product);

    Mono<ProductUpc> createProductUpc(ProductUpc productUpc);

    Flux<ProductUpc> getProductUpc(Integer bpnId);

    Flux<Product> getProduct(String... filterTypeAndValue);

    public Flux<ProductUpc> getProductUpcByRockId(String filterType, String rogId);
}
