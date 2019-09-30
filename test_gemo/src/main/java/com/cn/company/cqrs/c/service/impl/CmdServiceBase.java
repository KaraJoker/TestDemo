package com.cn.company.cqrs.c.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.highto.framework.ddd.SingletonEntityRepository;

public abstract class CmdServiceBase {

	@Autowired
	protected SingletonEntityRepository singletonEntityRepository;

}
