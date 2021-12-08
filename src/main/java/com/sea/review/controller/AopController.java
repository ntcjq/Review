package com.sea.review.controller;


import com.sea.review.aspect.SeaLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AopController {


    @SeaLog(module = "aop")
    @GetMapping("aop")
    public String aop(String name) {
        return name;
    }

}
