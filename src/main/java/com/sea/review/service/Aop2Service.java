package com.sea.review.service;

import com.sea.review.bean.Member;
import com.sea.review.dao.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class Aop2Service {

    @Autowired
    private MemberRepository memberRepository;

    @Transactional(rollbackFor = Exception.class)
    public void testAop() {
        System.out.println("Aop2Service");
        Member member = new Member();
        member.setUsername("asasasaasasasaasasasaasasasaasasasaasasasaasasasaasasasa");
        member.setEmail("1111");
        member.setRegTime(new Date());
        memberRepository.save(member);
        System.out.println("afterADD");


    }
}
