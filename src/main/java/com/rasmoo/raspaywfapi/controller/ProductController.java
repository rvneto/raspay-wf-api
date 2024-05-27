package com.rasmoo.raspaywfapi.controller;

import com.rasmoo.raspaywfapi.dto.ProductDto;
import com.rasmoo.raspaywfapi.model.Product;
import com.rasmoo.raspaywfapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RequestMapping("/v1/product")
@RestController
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<Mono<Void>> create(@RequestBody ProductDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(dto)
                        .then());
    }

    @GetMapping
    public ResponseEntity<Flux<Product>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @GetMapping("acronym/{acronym}")
    public ResponseEntity<Mono<Product>> findByAcronym(@PathVariable("acronym") String acronym) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findByAcronym(acronym));
    }

}
