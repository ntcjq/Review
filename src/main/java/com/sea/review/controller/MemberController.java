package com.sea.review.controller;


import com.sea.review.bean.Member;
import com.sea.review.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("add")
    public Long add(long id, String name) {
        System.out.println("==enter test");
        return memberService.add(id, name);
    }

    @GetMapping("list")
    public List<Member> list() {
        return memberService.getAll();
    }


}
