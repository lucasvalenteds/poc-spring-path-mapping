package com.example.spring.customer;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustomerControllerTest {

    private final WebTestClient client = WebTestClient.bindToController(new CustomerController()).build();

    @Test
    void testFindingAllCustomers() {
        List<Customer> customers = client.get()
            .uri("/customers")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(Customer.class)
            .returnResult()
            .getResponseBody();

        assertNotNull(customers);
        assertEquals(3, customers.size());
        assertEquals("John Smith", customers.get(0).getName());
        assertEquals("Mary Jane", customers.get(1).getName());
        assertEquals("Paul Anderson", customers.get(2).getName());
    }

    @Test
    void testFindingOneCustomer() {
        String customerId = "abc/123";

        Customer customer = client.get()
            .uri("/customers/{customerId}", customerId)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Customer.class)
            .returnResult()
            .getResponseBody();

        assertNotNull(customer);
        assertEquals("abc/123", customer.getId());
        assertEquals("John Smith", customer.getName());
    }
}
