package com.cn.company.controller;

import com.cn.company.cqrs.c.service.PtCmdService;
import com.cn.company.cqrs.q.bean.PT;
import com.cn.company.cqrs.q.service.PTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class StatisticsController {

    @Autowired
    PTService ptService;

    @Autowired
    PtCmdService ptCmdService;

    /**
     * @program: ${PROJECT_NAME}
     * @Description: MongoDB主从数据库展示
     * @Param: ${params}
     * @return: ${return}
     * @Author: Outcaster
     * @date: ${YEAR}-${MONTH}-${DAY} ${HOUR}:${MINUTE}
     */
    @RequestMapping("/demo")
    public List<Map> Statistics(){
        return ptService.statistics();
    }

    /**
     * @program: ${PROJECT_NAME}
     * @Description: 放入内存
     * @Param: ${params}
     * @return: ${return}
     * @Author: Outcaster
     * @date: ${YEAR}-${MONTH}-${DAY} ${HOUR}:${MINUTE}
     */
    @PostMapping("/recycleWatch")
    public void recycleWatch(@RequestBody PT pt){
        ptCmdService.recycleWatch(pt);
    }

    /**
     * @program: ${PROJECT_NAME}
     * @Description: 从内存取出
     * @Param: ${params}
     * @return: ${return}
     * @Author: Outcaster
     * @date: ${YEAR}-${MONTH}-${DAY} ${HOUR}:${MINUTE}
     */
    @GetMapping("/getWatch")
    public Map<String, Object> getWatch(){
        return ptCmdService.getWatch();
    }
}