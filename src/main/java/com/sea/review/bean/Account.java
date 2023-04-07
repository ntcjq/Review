package com.sea.review.bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Account {

    @Id
    private Long id;

    private String bankName;

    private String bankNo;


}
