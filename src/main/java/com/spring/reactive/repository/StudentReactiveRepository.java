package com.spring.reactive.repository;

import com.spring.reactive.entities.Student;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentReactiveRepository extends ReactiveMongoRepository<Student,String> {

    @Query(value = "{'studentName': {$regex : '^?0$', $options: 'i'}}")
    Mono<Student> findByQuery(String studentName);

}
