package com.sea.review.service;

import com.sea.review.dao.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbsTxService {

    @Autowired
    protected MemberRepository memberRepository;

}
