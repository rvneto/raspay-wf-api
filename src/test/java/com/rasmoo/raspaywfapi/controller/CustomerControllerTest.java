package com.rasmoo.raspaywfapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasmoo.raspaywfapi.dto.CustomerDto;
import com.rasmoo.raspaywfapi.model.Customer;
import com.rasmoo.raspaywfapi.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.Mockito.when;

@WebFluxTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    @Test
    void shouldReturnOnlyOneCustomerFilteredByCPF() throws JsonProcessingException {
        Customer customer = Customer.builder()
                .id("123456")
                .cpf("12345678901")
                .build();

        when(customerService.findAll("", "", "12345678901", 0, 1, "asc"))
                .thenReturn(Flux.just(customer));

        webTestClient.get().uri(uriBuilder ->
                        uriBuilder.path("/v1/customer")
                                .queryParam("pageNumber", 0)
                                .queryParam("pageSize", 1)
                                .queryParam("cpf", "12345678901")
                                .build()
                ).accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo(objectMapper.writeValueAsString(List.of(customer)));

    }

    @Test
    void shouldSaveNewCustomer() throws JsonProcessingException {

        Customer customerSaved = Customer.builder()
                .id("123456")
                .firstName("Roberto")
                .lastName("de Vargas")
                .email("robertovargas@gmail.com")
                .cpf("47969997015")
                .build();

        CustomerDto customerRequest = new CustomerDto(
                customerSaved.getFirstName(),
                customerSaved.getLastName(),
                customerSaved.getEmail(),
                customerSaved.getCpf());

        when(customerService.create(customerRequest))
                .thenReturn(Mono.just(customerSaved));

        webTestClient.post().uri("/v1/customer")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(customerRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(String.class)
                .isEqualTo(objectMapper.writeValueAsString(customerSaved));

    }

}
