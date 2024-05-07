package com.sea.review.service;

/**
 * @description:
 * @author: jiaqi.cui
 * @date: 2023/6/20
 */
public class Bird implements Flyable {
    @Override
    public void fly() throws Exception {
        System.out.println("bird fly");
    }
}
