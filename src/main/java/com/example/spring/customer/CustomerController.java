package com.example.spring.customer;

import com.example.spring.Fixtures;
import com.example.spring.car.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private static final Logger logger = LogManager.getLogger(CustomerController.class);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Customer> findCustomers() {
        logger.info("Returning all customers");

        return Flux.fromIterable(Fixtures.customers);
    }

    @GetMapping(path = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Customer> findCustomer(@PathVariable("customerId") String customerId) {
        logger.info("Returning customer with ID {}", customerId);

        return Flux.fromIterable(Fixtures.customers)
            .filter(customer -> customer.getId().equals(customerId))
            .singleOrEmpty();
    }

    @GetMapping(path = "/{customerId}/cars", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Car> findCustomerCars(@PathVariable("customerId") String customerId) {
        logger.info("Returning cars from customer with ID {}", customerId);

        return Flux.fromIterable(Fixtures.customersCars.getOrDefault(customerId, List.of()));
    }

    @GetMapping(path = "/{customerId}/cars/{carId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Car> findCustomerCars(@PathVariable("customerId") String customerId,
                                      @PathVariable("carId") String carId
    ) {
        logger.info("Returning car with ID {} from customer with ID {}", carId, customerId);

        return Flux.fromIterable(Fixtures.customersCars.getOrDefault(customerId, List.of()))
            .filter(car -> car.getId().equals(carId))
            .singleOrEmpty();
    }
}
