package com.rasmoo.raspaywfapi.controller;

import com.rasmoo.raspaywfapi.dto.CustomerDto;
import com.rasmoo.raspaywfapi.model.Customer;
import com.rasmoo.raspaywfapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Mono<Customer>> create(@RequestBody CustomerDto customerDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.create(customerDto));
    }

    @GetMapping
    public ResponseEntity<Flux<Customer>> findAll(
            @RequestParam(value = "firstName", required = false, defaultValue = "") String firstName,
            @RequestParam(value = "email", required = false, defaultValue = "") String email,
            @RequestParam(value = "cpf", required = false, defaultValue = "") String cpf,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize,
            @RequestParam(value = "sort", required = false, defaultValue = "asc") String order
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findAll(firstName, email, cpf, pageNumber, pageSize, order));

    }
}
