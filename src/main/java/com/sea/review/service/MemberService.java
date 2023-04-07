package com.sea.review.service;


import com.sea.review.bean.Member;
import com.sea.review.dao.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Long add(long id, String username) {
        Member member = new Member();
        member.setId(id);
        member.setUsername(username);
        member.setRegTime(new Date());
        memberRepository.save(member);
        return member.getId();
    }

    public List<Member> getAll() {
        return memberRepository.findAll();
    }
}
