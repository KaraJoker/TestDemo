package com.cn.company.cqrs.q.service;

import com.cn.company.cqrs.q.bean.PT;
import com.cn.company.cqrs.q.dao.PTDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PTService {

    @Autowired
    PTDao ptDao;

    public List<Map> statistics(){
        return ptDao.statistics();
    }

    public PT save(PT pt){
        return ptDao.save(pt);
    }
}
