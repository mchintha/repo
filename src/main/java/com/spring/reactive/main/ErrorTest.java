package com.spring.reactive.main;

import reactor.core.publisher.Flux;

public class ErrorTest {

    public static void main(String[] args) {

        Flux<Integer> just = Flux.just(3, 5, 0,9);
        just.map(i -> "100 / " + i + " = " + (100 / i)).onErrorReturn(e -> e instanceof ArithmeticException,"Arithmetic")
                .subscribe(v -> System.out.println(v));

        just.map(i -> 100 / i).onErrorResume(e -> {
            System.out.println("in error resume");
            return Flux.just(2, 5, 6).map(i -> 100 / i);
        }).subscribe(val -> System.out.println(val));

    }
}
