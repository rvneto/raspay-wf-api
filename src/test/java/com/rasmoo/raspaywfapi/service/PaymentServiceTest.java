package com.rasmoo.raspaywfapi.service;

import com.rasmoo.raspaywfapi.dto.CreditCardDto;
import com.rasmoo.raspaywfapi.dto.PaymentDto;
import com.rasmoo.raspaywfapi.enums.PaymentStatus;
import com.rasmoo.raspaywfapi.model.CreditCard;
import com.rasmoo.raspaywfapi.model.Customer;
import com.rasmoo.raspaywfapi.model.Order;
import com.rasmoo.raspaywfapi.model.Payment;
import com.rasmoo.raspaywfapi.repository.PaymentRepository;
import com.rasmoo.raspaywfapi.service.impl.PaymentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    CustomerService customerService;

    @Mock
    OrderService orderService;

    @Mock
    CreditCardService creditCardService;

    @Mock
    PaymentRepository paymentRepository;

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Test
    void shouldApprovePaymentWhenCreditCardIsFoundAndCustomerIdIsCorrect() {

        Customer customer = getCustomer();
        Order order = getOrder(customer.getId());
        CreditCardDto creditCardDto = new CreditCardDto("123", customer.getCpf(), "0123456789012345", 12, 28, 1);
        PaymentDto paymentDto = new PaymentDto(creditCardDto, customer.getId(), order.getId());

        when(customerService.findById(paymentDto.customerId())).thenReturn(Mono.just(customer));
        when(orderService.findById(paymentDto.orderId())).thenReturn(Mono.just(order));

        CreditCard creditCard = CreditCard.builder()
                .id("123456")
                .documentNumber("76748207002")
                .customerId(customer.getId())
                .build();

        when(creditCardService.findByNumber(creditCardDto.number())).thenReturn(Mono.just(creditCard));

        Payment paymentSaved = Payment.builder().status(PaymentStatus.APPROVED).build();
        when(paymentRepository.save(any())).thenReturn(Mono.just(paymentSaved));

        Mono<Payment> paymentMono = paymentService.process(paymentDto);
        StepVerifier.create(paymentMono)
                .expectNextMatches(payment -> payment.getStatus().equals(PaymentStatus.APPROVED))
                .verifyComplete();

        verify(customerService, times(1)).findById(paymentDto.customerId());
        verify(orderService, times(1)).findById(paymentDto.orderId());
        verify(creditCardService, times(1)).findByNumber(paymentDto.creditCard().number());
        verify(creditCardService, times(0)).create(any(), any());
        verify(paymentRepository, times(1)).save(any());

    }

    private Customer getCustomer() {
        return Customer.builder()
                .id("123456")
                .cpf("47969997015")
                .build();
    }

    private Order getOrder(String customerId) {
        return Order.builder()
                .id("123456")
                .customerId(customerId)
                .build();
    }

}
