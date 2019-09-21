package com.cn.company.config;

import com.cn.company.axon.aggregates.BankAccountAggregate;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.spring.config.EnableAxon;
import org.axonframework.spring.eventsourcing.SpringPrototypeAggregateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Created by Edison Xu on 2017/3/14.
 */
@EnableAxon
@Configuration
public class BankAccountConfig {

    @Autowired
    private EventStore eventStore;

    @Bean
    @Scope("prototype")
    public BankAccountAggregate bankAccountAggregate(){
        return new BankAccountAggregate();
    }

    @Bean
    public AggregateFactory<BankAccountAggregate> bankAccountAggregateAggregateFactory(){
        SpringPrototypeAggregateFactory<BankAccountAggregate> aggregateFactory = new SpringPrototypeAggregateFactory<>();
        aggregateFactory.setPrototypeBeanName("bankAccountAggregate");
        return aggregateFactory;
    }

    @Bean
    public Repository<BankAccountAggregate> bankAccountAggregateRepository(){
        EventSourcingRepository<BankAccountAggregate> repository = new EventSourcingRepository<BankAccountAggregate>(
                bankAccountAggregateAggregateFactory(),
                eventStore
        );
        return repository;
    }
}
