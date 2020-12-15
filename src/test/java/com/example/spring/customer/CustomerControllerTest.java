package com.example.spring.customer;

import com.example.spring.car.Car;
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

    @Test
    void testFindingCarsFromCustomer() {
        String customerId = "def/123";

        List<Car> cars = client.get()
            .uri("/customers/{customerId}/cars", customerId)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(Car.class)
            .returnResult()
            .getResponseBody();

        assertNotNull(cars);
        assertEquals(2, cars.size());
        assertEquals("xyz/100", cars.get(0).getId());
        assertEquals("Ford", cars.get(0).getBrand());
        assertEquals("xyz/101", cars.get(1).getId());
        assertEquals("Toyota", cars.get(1).getBrand());
    }
}
