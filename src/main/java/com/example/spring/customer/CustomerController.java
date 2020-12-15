package com.example.spring.customer;

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

    private static final List<Customer> customers = List.of(
        new Customer("abc/123", "John Smith", 45),
        new Customer("def/123", "Mary Jane", 32),
        new Customer("def/456", "Paul Anderson", 18)
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
}
