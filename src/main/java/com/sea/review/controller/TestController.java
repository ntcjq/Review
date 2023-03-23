package com.sea.review.controller;


import com.alibaba.fastjson.JSON;
import com.sea.review.bean.Person;
import com.sea.review.service.TestService;
import com.sea.review.service.TxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("test")
    public String test(String name) {
        System.out.println("==enter test");
        testService.testNo();
        Person person = new Person();
        person.setName("cjq");
        person.setBirth(new Date());
        return JSON.toJSONString(person);
    }


}
