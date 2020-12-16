package com.example.spring.customer;

import com.example.spring.car.Car;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustomerControllerTest {

    private final WebTestClient client = WebTestClient.bindToController(new CustomerController()).build();

    @Nested
    final class Encoded {
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
            String customerId = "def/456";

            Customer customer = client.get()
                .uri("/customers/{customerId}", Map.of("customerId", customerId))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Customer.class)
                .returnResult()
                .getResponseBody();

            assertNotNull(customer);
            assertEquals(customerId, customer.getId());
            assertEquals("Paul Anderson", customer.getName());
        }

        @Test
        void testFindingCarsFromCustomer() {
            String customerId = "def/123";

            List<Car> cars = client.get()
                .uri("/customers/{customerId}/cars", Map.of("customerId", customerId))
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

        @Test
        void testFindingCarFromCustomer() {
            String customerId = "abc/123";
            String carId = "xyz/102";

            Car car = client.get()
                .uri("/customers/{customerId}/cars/{carId}", Map.ofEntries(
                    Map.entry("customerId", customerId),
                    Map.entry("carId", carId)
                ))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Car.class)
                .returnResult()
                .getResponseBody();

            assertNotNull(car);
            assertEquals(carId, car.getId());
            assertEquals("Toyota", car.getBrand());
            assertEquals("compact", car.getCategory());
        }
    }
}
