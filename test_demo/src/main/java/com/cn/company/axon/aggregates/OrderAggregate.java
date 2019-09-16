package com.cn.company.axon.aggregates;

import com.cn.company.axon.model.OrderId;
import com.cn.company.axon.query.OrderCreatedEvent;
import com.cn.company.domain.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateMember;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.spring.stereotype.Aggregate;
import java.util.Map;
import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

/**
 * Created by Edison Xu on 2017/3/7.
 */
@Getter
@Aggregate
@AllArgsConstructor
public class OrderAggregate {

    @AggregateIdentifier
    private OrderId id;
    private String username;
    private double payment;

    @AggregateMember
    private Map<String, OrderProduct> products;

    public OrderAggregate(OrderId id, String username, Map<String, OrderProduct> products){
        this.id=id;
        this.username=username;
        this.products=products;
    }
    /**
     * Divided 100 here because of the transformation of accuracy
     *
     * @return
     */
    public double getPayment() {
        return payment/100;
    }

    public void addProduct(OrderProduct product){
        this.products.put(product.getId(), product);
        payment += product.getPrice() * product.getAmount();
    }

    public void removeProduct(String productId){
        OrderProduct product = this.products.remove(productId);
        payment = payment - product.getPrice() * product.getAmount();
    }
}
