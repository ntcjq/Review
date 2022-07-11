package com.sea.review.service;

import com.sea.review.bean.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class TxService extends AbsTxService {


    @Transactional
    public void tx() {
        Member member = new Member();
        member.setUsername("cui");
        member.setRegTime(new Date());
        memberRepository.save(member);
        System.out.println(1 / 0);
    }
}
