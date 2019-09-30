package com.edi.learn.axon;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Edison Xu on 2017/3/7.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.edi.learn"})
@EntityScan(basePackages = {"com.edi.learn",
        "org.axonframework.eventsourcing.eventstore.jpa",
        "org.axonframework.eventhandling.saga.repository.jpa",
        "org.axonframework.eventhandling.tokenstore.jpa"})
@EnableJpaRepositories(basePackages = {"com.edi.learn.axon.query"})
public class AxonApplication {

    public static void main(String args[]){
        SpringApplication.run(AxonApplication.class, args);
    }
}
