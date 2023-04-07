package com.sea.review.bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Member {

    @Id
    private Long id;

    private String username;

    private String email;

    private Date regTime;


}
