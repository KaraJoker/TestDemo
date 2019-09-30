package com.cn.company.cqrs.q.dao;

import com.cn.company.cqrs.q.bean.PT;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface PTDao {

    List<Map> statistics();

    void save(PT pt);
}
