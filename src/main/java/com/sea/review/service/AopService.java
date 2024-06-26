package com.sea.review.service;

import com.sea.review.bean.Member;
import com.sea.review.dao.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class AopService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private Aop2Service aop2Service;

    @Transactional(rollbackFor = Exception.class)
    public void testAop() {

        Member member = new Member();
        member.setUsername("asasasaasasasaasasasaasasasaasasasaasasasaasasasaasasasa");
        member.setEmail("asd");
        member.setRegTime(new Date());
        memberRepository.save(member);
        aop2Service.testAop();
        System.out.println("afterADD");


    }
}
