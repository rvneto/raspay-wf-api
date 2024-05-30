package com.rasmoo.raspaywfapi.service.impl;

import com.rasmoo.raspaywfapi.dto.OrderDto;
import com.rasmoo.raspaywfapi.exception.BadRequestException;
import com.rasmoo.raspaywfapi.exception.NotFoundException;
import com.rasmoo.raspaywfapi.mapper.OrderMapper;
import com.rasmoo.raspaywfapi.model.Order;
import com.rasmoo.raspaywfapi.repository.OrderRepository;
import com.rasmoo.raspaywfapi.service.CustomerService;
import com.rasmoo.raspaywfapi.service.OrderService;
import com.rasmoo.raspaywfapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper mapper;
    private final OrderRepository repository;
    private final CustomerService customerService;
    private final ProductService productService;

    @Override
    public Mono<Order> findById(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Order not found")));
    }

    @Override
    public Mono<Order> create(OrderDto dto) {
        return customerService.findById(dto.customerId())
                .flatMap(customer -> productService.findByAcronym(dto.productAcronym())
                        .flatMap(product -> {
                            Order order = mapper.toModel(dto);
                            if (dto.discount().intValue() > 0) {
                                if (dto.discount().compareTo(product.getCurrentPrice()) > 0) {
                                    return Mono.error(() -> new BadRequestException("Discount can not be greater than currentPrice"));
                                }
                                order.setOriginalPrice(product.getCurrentPrice().subtract(dto.discount()));
                            } else {
                                order.setOriginalPrice(product.getCurrentPrice());
                            }
                            order.setDtRegistedOrder(LocalDateTime.now());
                            order.setProductId(product.getId());
                            order.setCustomerId(customer.getId());
                            return repository.save(order);
                        }));
    }

}
