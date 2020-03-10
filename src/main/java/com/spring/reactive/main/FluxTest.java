package com.spring.reactive.main;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FluxTest {

    public static void main(String[] args) {
        Flux<Integer> just = Flux.just(2, 5, 9, 4);
        just.subscribe(i -> System.out.println(i));

        //with boolean
        Flux<Boolean> just1 = Flux.just(true, false, false);
        just1.subscribe(bool -> System.out.println(bool));

        // Creates a Flux from an already existing Iterable, for example a List.
        List<String> stringList = Arrays.asList("Hello", "foo", "bar");
        Flux<String> fluxFromList = Flux.fromIterable(stringList);

        // It works the same with Java Streams (which are not reactive).
        Stream<String> stringStream = stringList.stream();
        Flux<String> fluxFromStream = Flux.fromStream(stringStream);

        // Creates a flux on a range.
        Flux<Integer> rangeFlux = Flux.range(1, 5); // Flux(1, 2, 3, 4, 5)

        // Creates a flux that generates a new value every 100 ms.
        // The value is incremental, starting at 1.
        Flux<Long> intervalFlux = Flux.interval(Duration.ofMillis(100));

        // You can also create a Flux from another one, or from a Mono.
        Flux<String> fluxCopy = Flux.from(fluxFromList);
    }
}
