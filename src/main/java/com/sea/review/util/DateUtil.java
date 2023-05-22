package com.sea.review.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;


public class DateUtil {

    public static String curDateFmt() {
        return curDateFmt("yyyy-MM-dd HH:mm:ss");
    }

    public static String curDateFmt(String format) {
        return DateFormatUtils.format(new Date(), format);
    }

}
