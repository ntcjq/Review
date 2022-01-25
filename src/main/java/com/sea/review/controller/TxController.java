package com.sea.review.controller;

import com.sea.review.service.TxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TxController {

    @Autowired
    private TxService txService;


    @GetMapping("tx")
    public String tx() {

        txService.tx();
        return "success";
    }
}
