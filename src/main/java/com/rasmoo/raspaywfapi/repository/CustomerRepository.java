package com.rasmoo.raspaywfapi.repository;

import com.rasmoo.raspaywfapi.model.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {

    Mono<Customer>  findByEmail(String email);
}
