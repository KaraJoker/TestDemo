package com.cn.company;

import com.cn.company.cqrs.c.repository.SingletonEntityFactoryImpl;
import com.highto.framework.ddd.SingletonEntityRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ComponentScan(basePackages = {"com.cn.company"})
@EntityScan(basePackages = {"com.cn.company",
        "org.axonframework.eventsourcing.eventstore.jpa",
        "org.axonframework.eventhandling.saga.repository.jpa",
        "org.axonframework.eventhandling.tokenstore.jpa"})
@EnableJpaRepositories(basePackages = {"com.cn.company.plan"})
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class TestDemoApplication {

    @Bean
    public SingletonEntityRepository singletonEntityRepository() {
        SingletonEntityRepository singletonEntityRepository = new SingletonEntityRepository();
        singletonEntityRepository.setEntityFactory(new SingletonEntityFactoryImpl());
        return singletonEntityRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(TestDemoApplication.class, args);
    }
}
