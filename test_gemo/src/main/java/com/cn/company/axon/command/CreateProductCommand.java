package com.cn.company.axon.command;

/**
 * Created by Edison Xu on 2017/3/7.
 */
public class CreateProductCommand {

    private String id;
    private String name;
    private long price;
    private int stock;

    public CreateProductCommand(String id, String name, long price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }
}

