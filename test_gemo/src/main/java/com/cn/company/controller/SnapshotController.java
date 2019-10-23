package com.cn.company.controller;

import com.cn.company.cqrs.c.service.disruptor.CoreSnapshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/snapshot")
public class SnapshotController {

    @Autowired
    private CoreSnapshotService snapshotServicel;

    /**
     * @program: ${PROJECT_NAME}
     * @Description: 生成快照
     * @Param: ${params}
     * @return: ${return}
     * @Author: Outcaster
     * @date: ${YEAR}-${MONTH}-${DAY} ${HOUR}:${MINUTE}
     */
    @RequestMapping("/save")
    @ResponseBody
    public String saveSnapshot() {
        try {
            this.snapshotServicel.makeSnapshot();
        } catch (Throwable throwable){
            throwable.printStackTrace();
            return "error";
        }
        return "success";
    }

}
