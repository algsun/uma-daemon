package com.microwise.uma.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具
 *
 * @author houxiaocheng
 * @date 2013-4-24 下午3:54:42
 */
public class TimeUtil {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String MM_DD_HH_MM = "MM-dd HH:mm";
    public static final String HH_MM = "HH:mm";
    public static final String YYYY_MM = "yyyy-MM";

    private Date date;

    public TimeUtil() {
    }

    public TimeUtil(Date date) {
        this.date = date;
    }

    /**
     * 获取精确的毫秒
     *
     * @return
     * @deprecated 废弃 @gaohui 2013-09-23
     */
    public static Long exactNanoTime() {
        return System.currentTimeMillis();
    }

    /**
     * 以指定的方式格式化时间
     *
     * @param format 格式
     * @param date   时间
     * @return
     */
    public static String format(String format, Date date) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static Date parse(String date, String pattern) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.parse(date);
    }

    public static Date convert(Date date, String pattern) {
        Date value = new Date();
        try {
            value = parse(format(pattern, date), pattern);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return value;
    }

    public TimeUtil addSeconds(int seconds) {
        changeDate(Calendar.SECOND, seconds);
        return this;
    }

    public TimeUtil minusSeconds(int seconds) {
        changeDate(Calendar.SECOND, -seconds);
        return this;
    }

    public TimeUtil addDays(int days) {
        changeDate(Calendar.DAY_OF_YEAR, days);
        return this;
    }

    public TimeUtil minusDays(int days) {
        changeDate(Calendar.DAY_OF_YEAR, -days);
        return this;
    }

    private void changeDate(int type, int numbers) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(type, numbers);
        date = calendar.getTime();
    }

    public Date toDate() {
        return date;
    }
}
