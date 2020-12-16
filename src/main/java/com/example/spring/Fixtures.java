package com.example.spring;

import com.example.spring.car.Car;
import com.example.spring.customer.Customer;

import java.util.List;
import java.util.Map;

public final class Fixtures {

    private Fixtures() {
    }

    public static final List<Customer> customers = List.of(
        new Customer("abc/123", "John Smith", 45),
        new Customer("def/123", "Mary Jane", 32),
        new Customer("def/456", "Paul Anderson", 18)
    );

    public static final List<Car> cars = List.of(
        new Car("xyz/100", "Ford", "truck", 2005),
        new Car("xyz/101", "Toyota", "truck", 1979),
        new Car("xyz/102", "Toyota", "compact", 2016)
    );

    public static final Map<String, List<Car>> customersCars = Map.ofEntries(
        Map.entry(customers.get(0).getId(), List.of(cars.get(2))),
        Map.entry(customers.get(1).getId(), List.of(cars.get(0), cars.get(1))),
        Map.entry(customers.get(2).getId(), List.of())
    );

}
