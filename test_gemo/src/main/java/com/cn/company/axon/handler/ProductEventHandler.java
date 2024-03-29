package com.cn.company.axon.handler;

import com.cn.company.axon.command.CreateProductCommand;
import com.cn.company.axon.query.ProductCreatedEvent;
import com.cn.company.plan.entity.ProductEntry;
import com.cn.company.plan.repository.ProductEntryRepository;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * This event handler is used to update the repository data of the query side in the CQRS.
 * Created by Edison Xu on 2017/3/7.
 */
@Component
public class ProductEventHandler {

    private static final Logger LOGGER = getLogger(ProductEventHandler.class);

    @Autowired
    ProductEntryRepository repository;

    @CommandHandler
    public void handle(CreateProductCommand command) {
        apply(new ProductCreatedEvent(command.getId(),command.getName(),command.getPrice(),command.getStock()));
    }

    @EventHandler
    public void on(ProductCreatedEvent event){
        LOGGER.debug("repository data is updated");
        repository.save(new ProductEntry(event.getId(), event.getName(), event.getPrice(), event.getStock()));
    }
}
