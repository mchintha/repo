package com.spring.reactive.controller;

import com.spring.reactive.entities.Product;
import com.spring.reactive.entities.ProductUpc;
import com.spring.reactive.model.ProductResponse;
import com.spring.reactive.model.ProductResponseInfo;
import com.spring.reactive.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class WorkController {

    @Autowired
    private ProductService productService;

    @Value("#{'${product.filter.types}'.split(',')}")
    private List<String> productFilterTypes;

    @Value("#{'${productupc.filter.type}'.split(',')}")
    private List<String> productUPCFilterTypes;


    @PostMapping("/create")
    public Mono<Product> createProduct(@RequestBody Product product) {
     return productService.createProduct(product);
    }

    @PostMapping("/createupc")
    public Mono<ProductUpc> createProductUPC(@RequestBody ProductUpc productUpc) {
        return productService.createProductUpc(productUpc);
    }

    @GetMapping("/getProduct")
    public Flux<ProductResponseInfo> getProduct(@RequestParam(name = "filter",required = false) String filterType,
                                            @RequestParam(name = "value",required = false) String bpnId) {

        if(!filterType.isEmpty() && !bpnId.isEmpty()) {
            Flux<ProductResponseInfo> productResponseInfoFlux = productService.getProduct().flatMap(this::getProductResponse);
            return productResponseInfoFlux;
        }else if(validateFilterType(filterType) && productFilterTypes.contains(filterType))
            return productService.getProduct(filterType, bpnId).flatMap(p -> getProductResponse(p));
         else
            return productService.getProductUpcByRogId(filterType, bpnId).flatMap(this::getProductUPCResponse);
    }

    private Mono<ProductResponseInfo> getProductResponse(Product product) {
        return productService.getProductUpc(product.getBpnId()).collectList().map(productUpcs -> {
            List<ProductResponse> productResponsesList = new ArrayList<>();
            ProductResponse productResponse = new ProductResponse();
            productResponse.setProduct(product);
            productResponse.setProductUpcList(productUpcs);
            productResponsesList.add(productResponse);
            return productResponsesList;
        }).map(ProductResponseInfo::new);

    }

    private Mono<ProductResponseInfo> getProductUPCResponse(ProductUpc productUpc) {
        return productService.getProduct("bpnId", String.valueOf(productUpc.getBpnId())).collectList().map(product -> {
            List<ProductResponse> productResponsesList = new ArrayList<>();
            List<ProductUpc> list = new ArrayList<>();
            list.add(productUpc);
            ProductResponse productResponse = new ProductResponse();
            productResponse.setProduct(product.get(0));
            productResponse.setProductUpcList(list);
            productResponsesList.add(productResponse);
            return productResponsesList;
        }).map(ProductResponseInfo::new);

    }

    private boolean validateFilterType(String filterType) {
        if(productFilterTypes.contains(filterType) || productUPCFilterTypes.contains(filterType)) {
            return true;
        } else
            throw new RuntimeException("Filter Type Is Invalid");
    }
}
