package com.example.spring.customer;

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
import java.util.Map;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private static final Logger logger = LogManager.getLogger(CustomerController.class);

    private static final List<Customer> customers = List.of(
        new Customer("abc/123", "John Smith", 45),
        new Customer("def/123", "Mary Jane", 32),
        new Customer("def/456", "Paul Anderson", 18)
    );

    private static final List<Car> cars = List.of(
        new Car("xyz/100", "Ford", "truck"),
        new Car("xyz/101", "Toyota", "truck"),
        new Car("xyz/102", "Toyota", "compact")
    );

    private static final Map<String, List<Car>> customersCars = Map.ofEntries(
        Map.entry(customers.get(0).getId(), List.of(cars.get(2))),
        Map.entry(customers.get(1).getId(), List.of(cars.get(0), cars.get(1))),
        Map.entry(customers.get(2).getId(), List.of())
    );


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Customer> findCustomers() {
        logger.info("Returning all customers");

        return Flux.fromIterable(customers);
    }

    @GetMapping(path = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Customer> findCustomer(@PathVariable("customerId") String customerId) {
        logger.info("Returning customer with ID {}", customerId);

        return Flux.fromIterable(customers)
            .filter(customer -> customer.getId().equals(customerId))
            .singleOrEmpty();
    }

    @GetMapping(path = "/{customerId}/cars", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Car> findCustomerCars(@PathVariable("customerId") String customerId) {
        logger.info("Returning cars from customer with ID {}", customerId);

        return Flux.fromIterable(customersCars.getOrDefault(customerId, List.of()));
    }

    @GetMapping(path = "/{customerId}/cars/{carId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Car> findCustomerCars(@PathVariable("customerId") String customerId,
                                      @PathVariable("carId") String carId
    ) {
        logger.info("Returning car with ID {} from customer with ID {}", carId, customerId);

        return Flux.fromIterable(customersCars.getOrDefault(customerId, List.of()))
            .filter(car -> car.getId().equals(carId))
            .singleOrEmpty();
    }
}
