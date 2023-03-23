package com.sea.review.service;

import com.sea.review.aspect.SeaLog;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: jiaqi.cui
 * @date: 2023/3/23
 */
@Service
public class TestService {

    @SeaLog
    public void testNo() {
        System.out.println("==enter testNo");
    }

    @SeaLog
    public String testRe() {
        System.out.println("==enter testRe");
        return "testRe";
    }
}
