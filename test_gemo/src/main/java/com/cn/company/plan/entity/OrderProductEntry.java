package com.cn.company.plan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Edison Xu on 2017/3/15.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductEntry {

    @Id
    @GeneratedValue
    private Long jpaId;
    private String id;
    @Column
    private String name;
    @Column
    private long price;
    @Column
    private int amount;

    public OrderProductEntry(String id, String name, long price, int amount){
        this.id=id;
        this.name=name;
        this.price=price;
        this.amount=amount;
    }
}
