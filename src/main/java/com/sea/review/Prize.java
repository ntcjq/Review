package com.sea.review;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 奖品实体类
 */
@Data
@AllArgsConstructor
public class Prize {

    private int prizeId;//奖品ID
    private String prizeName;//奖品名称
    private double probability;//概率

}