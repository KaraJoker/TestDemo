package com.cn.company.plan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Remember to add {@code EntityScan }annotation in your start application.
 * <br>
 * Meanwhile, you need to add some packages in Axon Framework to make it work.
 * <br>
 * Created by Edison Xu on 2017/3/14.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntry {

    @Id
    private String id;
    @Column
    private String name;
    @Column
    private long price;
    @Column
    private int stock;
}
