package com.cn.company.cqrs.q.bean;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@Document(collection = "item")
public class Item implements Serializable {

    /**
     * 项目名称
     **/
    private String item_name;

    /**
     * 分数
     **/
    private double score;

    /**
     * 单位
     **/
    private String unit;
}
