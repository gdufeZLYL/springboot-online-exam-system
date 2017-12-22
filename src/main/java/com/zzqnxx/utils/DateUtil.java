package com.zzqnxx.utils;


import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 提供日期处理的简单工具类
 *
 * @author Qexz
 */
public class DateUtil {

    public static SimpleDateFormat monthSdf = new SimpleDateFormat("yyyyMM");
    public static SimpleDateFormat dateSdf = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat hourSdf = new SimpleDateFormat("yyyyMMddHH");
    public static SimpleDateFormat hourOnlySdf = new SimpleDateFormat("HH");

    public static final int MINUTE_MILLI_SECOND = 60 * 1000;
    public static final int HOUR_MILLI_SECOND = MINUTE_MILLI_SECOND * 60;
    public static final int DAY_MILLI_SECOND = HOUR_MILLI_SECOND * 24;

    /**
     * 取得当天时间的最大值
     *
     * @return
     */
    public static Date getCeilingOfCurrentDay() {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
        return calendar.getTime();
    }


    /**
     * 取得当天时间的最小值
     *
     * @return
     */
    public static Date getFloorOfCurrentDay() {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
        return calendar.getTime();
    }

    /**
     * 取得该时间的一日内的最小值
     *
     * @param date
     * @return
     */
    public static Date getFloorOfDay(Date date) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
        return calendar.getTime();
    }


    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getNowTime() {
        String DATA_FORMAT_DAY_TIME = "yyyy-MM-dd 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat(DATA_FORMAT_DAY_TIME);
        return sdf.format(new Date());
    }

    public static String today() {
        String DATA_FORMAT_DAY_TIME = "yyyyMMdd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATA_FORMAT_DAY_TIME);
        return sdf.format(new Date());
    }

    public static long getTime() {
        return new Date().getTime();
    }

    public static String getNowTimeStr() {
        String DATA_FORMAT_DAY_TIME = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATA_FORMAT_DAY_TIME);
        return sdf.format(new Date());
    }

    public static long getFormattedTime(String timeStr) {
        String DATA_FORMAT_DAY_TIME = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATA_FORMAT_DAY_TIME);
        try {
            return sdf.parse(timeStr).getTime();
        } catch (ParseException e) {
            System.err.println("convert time error: " + timeStr + " , return now time");
            return new Date().getTime();
        }
    }

    public static int getDaysOfLastMonth() {
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            String date = sdf.format(new Date().getTime());
            int lastMonth = Integer.parseInt(date.split("-")[1]) - 1;
            int year = Integer.parseInt(date.split("-")[0]);
            String lastMonthDate = year + "-" + lastMonth;
            calendar.setTime(sdf.parse(lastMonthDate));

            return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    //拿到下个月份的第一天，要考虑到跨年
    public static String getNextMonth(String curMonth) {
        String nextMonth = "all";
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(monthSdf.parse(curMonth));
            int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            long monthTime = days * 86400000L + monthSdf.parse(curMonth).getTime();
            calendar.setTime(new Date(monthTime));

            String yearStr = String.valueOf(calendar.get(Calendar.YEAR));
            String monthStr = formatMonth(calendar.get(Calendar.MONTH) + 1);

            nextMonth = yearStr + monthStr;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return nextMonth;
    }

    //把月份都转成两位
    public static String formatMonth(int time) {
        String timeStr = null;
        if (time < 10) {
            timeStr = "0" + time;
        } else {
            timeStr = String.valueOf(time);
        }
        return timeStr;
    }

    /**
     * 获取日期的偏移
     *
     * @param date 基准日期
     * @param dist 偏移量（可为负数）
     * @return
     */
    public static String dayDist(Date date, int dist) {
        Date a = DateUtils.addDays(date, dist);
        return dateSdf.format(a);
    }

    //获取上一个月的月分
    public static String getLastMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return monthSdf.format(cal.getTime());
    }

    public static Date yestoday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    public static LocalDateTime localDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static String monthString(Date date) {
        return String.valueOf(monthYearInt(date));
    }

    public static int monthYearInt(Date date) {
        LocalDateTime time = localDateTime(date);
        return time.getYear() * 100 + time.getMonthValue();
    }

}
