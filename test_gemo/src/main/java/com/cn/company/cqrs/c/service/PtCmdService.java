package com.cn.company.cqrs.c.service;

import com.cn.company.cqrs.q.bean.PT;
import org.springframework.stereotype.Service;


@Service
public interface PtCmdService {

	void recycleWatch(PT pt);
}
