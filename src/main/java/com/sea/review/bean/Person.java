package com.sea.review.bean;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class Person {

    private String name;
    private Integer age;
    private Date birth;
    private List<Account> addr;
    private Set<String> set;

}
