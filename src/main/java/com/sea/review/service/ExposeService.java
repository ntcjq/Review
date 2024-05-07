package com.sea.review.service;

import com.sea.review.aspect.SeaLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: jiaqi.cui
 * @date: 2024/5/7
 */
@Slf4j
@Service
public class ExposeService {

    @SeaLog
    public void exposeOne() {
        log.info("exposeOne start");
        //内部调用，让AOP也生效
        ((ExposeService) AopContext.currentProxy()).exposeTwo();
        log.info("exposeOne end");
    }

    @SeaLog
    public void exposeTwo() {
        log.info("exposeTwo start");

        log.info("exposeTwo end");
    }
}
