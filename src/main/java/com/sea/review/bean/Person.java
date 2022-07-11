package com.sea.review.bean;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Person {

    private String name;
    private Integer age;
    private Date birth;
    private List<Account> addr;

}
