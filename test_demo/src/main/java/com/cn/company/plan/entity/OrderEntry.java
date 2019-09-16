package com.cn.company.plan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Map;

/**
 * Created by Edison Xu on 2017/3/15.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntry {
    @Id
    private String id;
    @Column
    private String username;
    @Column
    private double payment;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    @MapKey(name = "id")
    private Map<String, OrderProductEntry> products;

    public OrderEntry(String id, String username, Map<String, OrderProductEntry> products){
        this.id=id;
        this.username=username;
        this.products=products;
    }
}
