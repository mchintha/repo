package com.spring.reactive.main;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class FluxMap {

    public static void main(String[] args) {
        Flux<Integer> range = Flux.range(1, 10);
        Flux<Integer> map = range.map(i -> i * i);

        map.subscribe(val -> System.out.println(val));

        //Another

        Flux<Integer> range1 = Flux.range(1, 10);
        Mono<List<Integer>> listMono = range1.map(i -> i * i).collectList();
        listMono.subscribe(val -> System.out.println(val));

        //Another
    }
}
