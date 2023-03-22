package com.sea.review.controller;


import com.alibaba.fastjson.JSON;
import com.sea.review.aspect.SeaLog;
import com.sea.review.bean.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class TestController {


    @SeaLog
    @GetMapping("test")
    public String test(String name) {
        Person person = new Person();
        person.setName("cjq");
        person.setBirth(new Date());
        return JSON.toJSONString(person);
    }


}
