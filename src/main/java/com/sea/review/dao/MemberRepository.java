package com.sea.review.dao;

import com.sea.review.bean.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long> {


}
