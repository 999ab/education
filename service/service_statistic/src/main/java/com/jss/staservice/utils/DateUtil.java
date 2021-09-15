package com.jss.staservice.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static final String dateFormat = "yyyy-MM-dd";

    public static String formatDate(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);

    }

    public static Date addDateS(Date date,int amount){
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE,now.get(Calendar.DATE)+amount);
        return now.getTime();
    }
}
