package com.rasmoo.raspaywfapi.service;

import com.rasmoo.raspaywfapi.dto.OrderDto;
import com.rasmoo.raspaywfapi.model.Order;
import reactor.core.publisher.Mono;

public interface OrderService {

    Mono<Order> findById(String id);

    Mono<Order> create(OrderDto dto);

}
