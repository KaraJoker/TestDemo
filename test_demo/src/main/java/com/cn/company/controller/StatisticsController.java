package com.cn.company.controller;

import com.cn.company.cqrs.q.bean.PT;
import com.cn.company.cqrs.q.service.PTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class StatisticsController {

    @Autowired
    PTService ptService;

    @RequestMapping("/demo")
    public List<Map> Statistics(){
        return ptService.statistics();
    }

    @PostMapping("/save")
    public void save(@RequestBody PT pt){
        ptService.save(pt);
    }
}
