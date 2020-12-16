package com.example.spring.car;

import com.example.spring.Fixtures;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(path = "/cars", produces = MediaType.APPLICATION_JSON_VALUE)
public class CarController {

    private static final Logger logger = LogManager.getLogger(CarController.class);

    @GetMapping("/search/full-text/{*query}")
    public Flux<Car> searchByBrandAndCategory(@PathVariable("query") String query) {
        String querySafe = query.trim().substring(1);

        logger.info("Searching cars using a query text: {}", querySafe);

        return Flux.fromIterable(Fixtures.cars)
            .filter(car -> car.getBrand().contains(querySafe) || car.getCategory().contains(querySafe));
    }

    @GetMapping("/search/release-year/{begin:[0-9]{4}}-{end:[0-9]{4}}")
    public Flux<Car> searchByReleaseYear(@PathVariable("begin") Integer begin,
                                         @PathVariable("end") Integer end
    ) {
        logger.info("Searching cars by release year range: between {} and {}", begin, end);

        return Flux.fromIterable(Fixtures.cars)
            .filter(car -> car.getReleaseYear() >= begin && car.getReleaseYear() <= end);
    }
}
