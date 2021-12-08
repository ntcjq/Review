package com.sea.review.controller;


import com.sea.review.aspect.SeaLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @GetMapping("test")
    public String test(String name) {
        return name;
    }

}
