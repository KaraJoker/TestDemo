package com.cn.company.axon.handler;

import com.cn.company.axon.aggregates.OrderAggregate;
import com.cn.company.axon.aggregates.ProductAggregate;
import com.cn.company.axon.command.CreateOrderCommand;
import com.cn.company.axon.query.OrderCreatedEvent;
import com.cn.company.domain.OrderProduct;
import com.cn.company.plan.entity.OrderEntry;
import com.cn.company.plan.entity.OrderProductEntry;
import com.cn.company.plan.entity.ProductEntry;
import com.cn.company.plan.repository.OrderEntryRepository;
import com.cn.company.plan.repository.ProductEntryRepository;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Aggregate;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * This event handler is used to update the repository data of the query side in the CQRS.
 * Created by Edison Xu on 2017/3/15.
 */
@Component
public class OrderEventHandler {

    private static final Logger LOGGER = getLogger(OrderEventHandler.class);

    @Autowired
    private OrderEntryRepository orderEntryRepository;

    @Autowired
    private ProductEntryRepository productEntryRepository;

    @Autowired
    private Repository<OrderAggregate> orderRepository;

    @Autowired
    private Repository<ProductAggregate> productRepository;

    @Autowired
    private EventBus eventBus;

    @EventHandler
    public void on(OrderCreatedEvent event){
        Map<String, OrderProductEntry> map = new HashMap<>();
        event.getProducts().forEach((id, product)->{
            map.put(id,
                    new OrderProductEntry(
                            product.getId(),
                            product.getName(),
                            product.getPrice(),
                            product.getAmount()));
        });
        OrderEntry order = new OrderEntry(event.getOrderId().toString(), event.getUsername(), map);
        orderEntryRepository.save(order);
    }

    @CommandHandler
    public void handle(CreateOrderCommand command) throws Exception {
        Map<String, OrderProduct> products = new HashMap<>();
        command.getProducts().forEach((productId,number)->{
            LOGGER.debug("Loading product information with productId: {}",productId);
            Optional<ProductEntry> aggregate = productEntryRepository.findById(productId);
            products.put(productId,
                    new OrderProduct(productId,
                            aggregate.get().getName(),
                            aggregate.get().getPrice(),
                            number));
        });
        orderRepository.newInstance(() -> new OrderAggregate(command.getOrderId(), command.getUsername(), products));
    }
}
