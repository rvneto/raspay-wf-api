package com.rasmoo.raspaywfapi.model;

import com.rasmoo.raspaywfapi.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("payments")
public class Payment {

    @Id
    private String id;

    private PaymentStatus status;

    private LocalDateTime dtRegistedPayment;

    @DBRef
    private CreditCard creditCard;

    @DBRef
    private Order order;

    @DBRef
    private Customer customer;

}
