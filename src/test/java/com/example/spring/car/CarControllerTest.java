package com.example.spring.car;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CarControllerTest {

    private final WebTestClient client = WebTestClient.bindToController(new CarController()).build();

    @Nested
    final class Encoded {

        @Test
        void testSearchingByCategory() {
            List<Car> cars = client.get()
                .uri("/cars/search/full-text/{query}", Map.of("query", "truck"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Car.class)
                .returnResult()
                .getResponseBody();

            assertNotNull(cars);
            assertEquals(2, cars.size());
            assertEquals("Ford", cars.get(0).getBrand());
            assertEquals("Toyota", cars.get(1).getBrand());
        }

        @Test
        void testSearchingByBrand() {
            List<Car> cars = client.get()
                .uri("/cars/search/full-text/{query}", Map.of("query", "Toy"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Car.class)
                .returnResult()
                .getResponseBody();

            assertNotNull(cars);
            assertEquals(2, cars.size());
            assertEquals("Toyota", cars.get(0).getBrand());
            assertEquals("Toyota", cars.get(1).getBrand());
        }

        @Test
        void testSearchingByReleaseYearRange() {
            List<Car> cars = client.get()
                .uri("/cars/search/release-year/{begin}-{end}", Map.ofEntries(
                    Map.entry("begin", 2000),
                    Map.entry("end", 2010)
                ))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Car.class)
                .returnResult()
                .getResponseBody();

            assertNotNull(cars);
            assertEquals(1, cars.size());
            assertEquals("Ford", cars.get(0).getBrand());
        }
    }
}
