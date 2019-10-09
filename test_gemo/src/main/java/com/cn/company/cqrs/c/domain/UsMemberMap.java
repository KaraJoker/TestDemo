package com.cn.company.cqrs.c.domain;

import com.cn.company.cqrs.q.bean.PT;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @program: TestDemo
 * @description: 在线用户
 * @author: Outcaster
 * @create: 2019-10-08 16:39
 */
public class UsMemberMap {

    private Map<String, Object> memberMap = new HashMap();

    private static final String uuid = UUID.randomUUID().toString().replace("-", "") + "_member";

    public void recycleWatch(PT pt) {
        this.memberMap.put(uuid,pt);
    }

    public Object getWatch() {
        return memberMap.get(uuid);
    }
}


