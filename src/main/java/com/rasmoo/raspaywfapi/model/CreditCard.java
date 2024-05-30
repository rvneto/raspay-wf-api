package com.rasmoo.raspaywfapi.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("credit-cards")
public class CreditCard {

    @Id
    private String id;

    @NotBlank
    private String number;

    private Long cvv;

    private int year;

    private String documentNumber;

    private int installments;

    private String customerId;

}
