package com.example.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@EnableWebFlux
@ComponentScan("com.example.spring")
@PropertySource("classpath:application.properties")
public class AppConfiguration implements WebFluxConfigurer {
}
