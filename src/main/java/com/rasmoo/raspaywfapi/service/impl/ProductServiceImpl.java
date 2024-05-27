package com.rasmoo.raspaywfapi.service.impl;

import com.rasmoo.raspaywfapi.dto.ProductDto;
import com.rasmoo.raspaywfapi.mapper.ProductMapper;
import com.rasmoo.raspaywfapi.model.Product;
import com.rasmoo.raspaywfapi.repository.ProductRepository;
import com.rasmoo.raspaywfapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Override
    public Mono<Product> create(ProductDto dto) {
        return repository.save(mapper.toModel(dto));
    }

    @Override
    public Flux<Product> findAll() {
        return repository.findAll();
    }

}
