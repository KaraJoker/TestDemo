package com.cn.company.cqrs.c.domain;

import cn.hutool.core.util.ObjectUtil;
import com.cn.company.cqrs.q.bean.PT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @program: TestDemo
 * @description: 在线用户
 * @author: Outcaster
 * @create: 2019-10-08 16:39
 */
public class UsMemberMap {

    private Map<String, Object> memberMap = new HashMap();

    public UsMemberMap() {
    }

    private static final String uuid = UUID.randomUUID().toString().replace("-", "") + "_member";

    public void recycleWatch(PT pt) {
        this.memberMap.put(uuid,pt);
    }

    public Map<String, Object> getWatch() {
        PT pt=(PT)memberMap.get(uuid);
        if(ObjectUtil.isNotNull(pt)) {
            pt.setBatch("斥候");
            pt.setClass_name("Ring_Buffer");
            pt.setGrade_name("A-4");
            pt.setClass_id(uuid);
            pt.setStudent_name("Outcaster");
            pt.setStudent_Id(uuid);
            pt.setGrade_id(uuid);
            pt.setSchool_id(uuid);
        }
        return memberMap;
    }
}


