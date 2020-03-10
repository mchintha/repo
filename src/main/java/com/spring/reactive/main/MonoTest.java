package com.spring.reactive.main;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoTest {

    public static void main(String[] args) {

        //creating empty Mono
        Mono<Object> empty = Mono.empty();
        empty.subscribe(val -> System.out.println(val));//Nothing will print

        //If we want to pass object which might be null
        Mono<Object> objectMono = Mono.justOrEmpty(null);
        objectMono.subscribe(val -> System.out.println(val));//Nothing will print

        //Creating Mono with just() method
        Mono<String> stringMono = Mono.just("Welcome");
        stringMono.subscribe(val -> {
            System.out.println(val);
            if(false) {
                throw new RuntimeException("exceptin in subscrier");
            }
        },error -> System.out.println(error.getMessage()),
                () -> System.out.println("Done"));

        //creating MONO from callable

        Mono<String> stringMono1 = Mono.fromCallable(() -> "Hello World");
        stringMono1.subscribe(val -> System.out.println(val));

        //creating Mono from another Mono

        Mono<String> from = Mono.from(stringMono1);
        from.subscribe(val -> System.out.println(val));

        //creating Mono from Flux
        Mono<Integer> monoFromFlux = Mono.from(Flux.range(1, 10));
        monoFromFlux.subscribe(i -> System.out.println(i));
       // The above Mono contains the first value of the Flux.


        //
        Flux<String> just = Flux.just("abc", "bbc", "xyz");



    }
}
