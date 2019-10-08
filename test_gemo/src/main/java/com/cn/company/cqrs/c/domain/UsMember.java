package com.cn.company.cqrs.c.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: TestDemo
 * @description: 用户
 * @author: Outcaster
 * @create: 2019-10-08 16:40
 */
@Data
@NoArgsConstructor
public class UsMember {
    private String id;
    private String nickName;
    private String password;
    private String avatar;
    private String state;
    private long joinTime;
}
