package com.highto.framework.web;
//设计方案：
// 1.泛型类 + 泛型方法  2.普通类 + 泛型方法  3.不使用泛型  哪种好？？

public interface DispatchMessageController {
    boolean doDispatch(String message, Response response);
}
