package com.sea.review;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 抽奖工具类
 */
public class LotteryUtil {


    public static Prize lottery(List<Prize> prizeList) {
        if (prizeList.isEmpty()) {
            return null;
        }
        //计算总概率
        double sumProbability = 0d;
        for (Prize award : prizeList) {
            sumProbability += award.getProbability();
        }
        //计算每个奖品的概率区间
        List<Double> sortPrizeProbabilityList = new ArrayList();
        Double tempSumProbability = 0d;
        for (Prize award : prizeList) {
            tempSumProbability += award.getProbability();
            sortPrizeProbabilityList.add(tempSumProbability / sumProbability);
        }

        //随机数在哪个概率区间内，则是哪个奖品
        double randomDouble = Math.random();
        //加入到概率区间中，排序后，返回的下标则是prizeList中中奖的下标
        sortPrizeProbabilityList.add(randomDouble);
        Collections.sort(sortPrizeProbabilityList);
        int lotteryIndex = sortPrizeProbabilityList.indexOf(randomDouble);
        return prizeList.get(lotteryIndex);
    }

    public static void main(String[] args) {
        List<Prize> prizeList = new ArrayList();
        prizeList.add(new Prize(1, "奖品A", 0.5));
        prizeList.add(new Prize(2, "奖品B", 0.2));
        prizeList.add(new Prize(3, "奖品C", 0.15));
        prizeList.add(new Prize(4, "奖品D", 0.1));
        prizeList.add(new Prize(5, "奖品E", 0.05));
        for (int i = 0; i < 10000; i++) {
            Prize prize = lottery(prizeList);
            System.out.println(prize.getPrizeName());
        }
    }
}
