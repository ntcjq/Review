package com.sea.review.util;

import cn.hutool.core.date.DateTime;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

public class TestUtil {


    public static void main(String[] args) throws ParseException {


//        System.out.println(PinyinUtil.getPinyin("实践是检验整理的唯一标准"));

        Date now = new Date();
        DateTime expireTime = cn.hutool.core.date.DateUtil.endOfDay(DateUtils.addDays(now, -1));
        System.out.println(expireTime);


        String tempName = "2023.12";
        String[] split = tempName.split("\\.");
        int month = Integer.parseInt(split[1]);
        if (month > 9) {
            System.out.println(split[0] + "-" + month);
        } else {
            System.out.println(split[0] + "-0" + month);
        }

    }


    public static void test(long l) {
        System.out.println(l);
    }

    public static void test2(int l) {
        System.out.println(l);
    }


}
