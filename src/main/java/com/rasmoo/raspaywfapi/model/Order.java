package com.rasmoo.raspaywfapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("orders")
public class Order {

    @Id
    private String id;

    private BigDecimal originalPrice;

    private BigDecimal discount;

    private LocalDateTime dtRegistedOrder;

    private String customerId;

    private String productId;
}
