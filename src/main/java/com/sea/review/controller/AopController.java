package com.sea.review.controller;


import com.sea.review.service.AopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AopController {

    @Autowired
    private AopService aopService;

    @GetMapping("aop")
    public String aop() {
        aopService.testAop();
        return "SUCCESS";
    }

}
