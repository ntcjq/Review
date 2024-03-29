package com.sea.review.controller;


import com.sea.review.service.LockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/lock")
public class LockController {
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    @Autowired
    private LockService lockService;

    @GetMapping("increment")
    public int increment(int amount) {
        return lockService.increment(amount);
    }

    @GetMapping("decrement")
    public int decrement(int amount) {

        return lockService.decrement(amount);
    }

    @GetMapping("get")
    public int getMoney() {

        return lockService.getMoney();
    }
}
