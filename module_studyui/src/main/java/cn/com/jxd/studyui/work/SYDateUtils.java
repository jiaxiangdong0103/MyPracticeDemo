package cn.com.jxd.studyui.work;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author xiangdong.jia
 * @description:
 * @date :2021/5/28 下午6:04
 */
public class SYDateUtils {

    public static final String FORMAT_YYYY_MM_DD_1 = "yyyy/MM/dd";
    public static final String FORMAT_YYYY_MM_DD_2 = "yyyy.MM.dd";
    public static final String FORMAT_YYYY_MM_DD_3 = "yyyy-MM-dd";
    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";
    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS_2 = "yyyy.MM.dd HH:mm:ss";
    public static final String FORMAT_YYYY_MM_DD_HH_MM_1 = "yyyy-MM-dd HH:mm";
    public static final String HH_MM_SS = "HH:mm:ss";
    public static final String HH_MM = "HH:mm";
    /**
     * time
     */
    public static final long TIME_MONTH = 4 * 7 * 24 * 60 * 60 * 1000;
    public static final long TIME_WEEK = 7 * 24 * 60 * 60 * 1000;
    public static final long TIME_DAY = 24 * 60 * 60 * 1000;
    public static final long TIME_HOUR = 60 * 60 * 1000;
    public static final long TIME_MINUTE = 60 * 1000;
    public static final long TIME_SECOND = 1000;

    private static final ThreadLocal<SimpleDateFormat> SDF_THREAD_LOCAL = new ThreadLocal<>();

    private static SimpleDateFormat getDefaultFormat() {
        SimpleDateFormat simpleDateFormat = SDF_THREAD_LOCAL.get();
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            SDF_THREAD_LOCAL.set(simpleDateFormat);
        }
        return simpleDateFormat;
    }

    public static SimpleDateFormat getDefaultFormat(String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return simpleDateFormat;
    }

    /**
     * 将时间戳转为时间字符串
     */
    public static String millis2String(long millis) {
        return millis2String(millis, getDefaultFormat());
    }

    /**
     * 将时间戳转为时间字符串
     *
     * @param millis
     * @param format
     * @return 时间字符串
     */
    public static String millis2String(final long millis, @NonNull final DateFormat format) {
        return format.format(new Date(millis));
    }

    /**
     * 将时间戳转为时间字符串
     *
     * @param millis
     * @param format
     * @return 时间字符串
     */
    public static String millis2String(final long millis, @NonNull String format) {
        return millis2String(millis, new SimpleDateFormat(format, Locale.getDefault()));
    }

    /**
     * 将时间字符串转为时间戳
     *
     * @param time time string.
     * @return 时间戳
     */
    public static long string2Millis(final String time) {
        return string2Millis(time, getDefaultFormat());
    }

    /**
     * 将时间字符串转为时间戳
     *
     * @param time
     * @param format
     * @return 时间戳
     */
    public static long string2Millis(final String time, @NonNull final DateFormat format) {
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * String 转 Date
     *
     * @param time
     * @return
     */
    public static Date string2Date(final String time) {
        return string2Date(time, getDefaultFormat());
    }

    /**
     * String 转 Date
     *
     * @param time
     * @param format
     * @return
     */
    public static Date string2Date(final String time, @NonNull final DateFormat format) {
        try {
            return format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将 Date 类型转为时间字符串
     *
     * @param date .
     * @return 时间字符串
     */
    public static String date2String(final Date date) {
        return date2String(date, getDefaultFormat());
    }

    /**
     * 将 Date 类型转为时间字符串
     *
     * @param date
     * @param format
     * @return 时间字符串
     */
    public static String date2String(final Date date, @NonNull final DateFormat format) {
        return format.format(date);
    }

    /**
     * 将 Date 类型转为时间戳
     *
     * @param date
     * @return 时间戳
     */
    public static long date2Millis(final Date date) {
        return date.getTime();
    }

    /**
     * 将时间戳转为 Date 类型
     *
     * @param millis
     * @return Date
     */
    public static Date millis2Date(final long millis) {
        return new Date(millis);
    }

    /**
     * 获取当前毫秒时间戳
     *
     * @return
     */
    public static long getNowMills() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间字符串
     *
     * @return
     */
    public static String getNowString() {
        return millis2String(System.currentTimeMillis(), getDefaultFormat());
    }

    /**
     * 获取当前 时间字符串
     *
     * @param format
     * @return
     */
    public static String getNowString(@NonNull final DateFormat format) {
        return millis2String(System.currentTimeMillis(), format);
    }

    /**
     * 获取当前 Date
     *
     * @return
     */
    public static Date getNowDate() {
        return new Date();
    }


    /**
     * 判断是否是今天
     *
     * @param time
     * @return
     */
    public static boolean isToday(final String time) {
        return isToday(string2Millis(time, getDefaultFormat()));
    }

    /**
     * 判断是否是今天
     *
     * @param time
     * @param format
     * @return
     */
    public static boolean isToday(final String time, @NonNull final DateFormat format) {
        return isToday(string2Millis(time, format));
    }

    /**
     * 判断是否是今天
     *
     * @param date T
     * @return
     */
    public static boolean isToday(final Date date) {
        return isToday(date.getTime());
    }

    /**
     * 判断是否是今天
     *
     * @param millis
     * @return
     */
    public static boolean isToday(final long millis) {
        long wee = getWeeOfToday();
        return millis >= wee && millis < wee + (24 * 60 * 60 * 1000);
    }

    private static long getWeeOfToday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 判断是否是今年
     *
     * @return
     */
    public static boolean isCommonYear(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        return getCurrentYear() == cal.get(Calendar.YEAR);
    }

    /**
     * 获取当前年
     *
     * @return
     */
    public static int getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取当前月
     *
     * @return
     */
    public static int getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH);
    }

    /**
     * 获取当前日
     *
     * @return
     */
    public static int getCurrentDay() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DATE);
    }

    /**
     * 获取星期几
     *
     * @param time
     * @return
     */
    public static String getChineseWeek(final String time) {
        return getChineseWeek(string2Date(time, getDefaultFormat()));
    }

    /**
     * 获取星期几
     *
     * @param time   T
     * @param format
     * @return
     */
    public static String getChineseWeek(final String time, @NonNull final DateFormat format) {
        return getChineseWeek(string2Date(time, format));
    }

    /**
     * 获取星期几
     *
     * @param date
     * @return
     */
    public static String getChineseWeek(final Date date) {
        return new SimpleDateFormat("E", Locale.CHINA).format(date);
    }

    /**
     * 获取星期几
     *
     * @param millis
     * @return
     */
    public static String getChineseWeek(final long millis) {
        return getChineseWeek(new Date(millis));
    }

    /**
     * 获取时间差
     *
     * @param millis1
     * @param millis2
     * @param showEmptyTime 是否显示为0的时间
     * @return 返回0天1小时5分钟12秒
     */
    public static String differentTime(long millis1, long millis2, boolean showEmptyTime) {
        String[] units = {"天", "小时", "分钟", "秒"};
        long[] unitLen = {TIME_DAY, TIME_HOUR, TIME_MINUTE, TIME_SECOND};
        return differentTime(millis1, millis2, showEmptyTime, units, unitLen);
    }

    public static String differentTime(long millis1, long millis2, boolean showEmptyTime, String[] units, long[] unitLen) {
        if (units == null || units.length == 0) {
            return "";
        }
        long millis = Math.abs(millis1 - millis2);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < units.length; i++) {
            if (millis >= unitLen[i]) {
                long mode = millis / unitLen[i];
                millis -= mode * unitLen[i];
                if (mode == 0 && !showEmptyTime) {
                    break;
                }
                sb.append(mode).append(units[i]);
            }
        }
        return sb.toString();
    }

    /**
     * 计算两个时间戳直接差几天
     *
     * @param millis1
     * @param millis2
     * @return
     */
    public static int differentTime(long millis1, long millis2) {

        long millis = millis1 - millis2;

        return (int) (millis / 3600 / 1000 / 24);
    }

    /**
     * 判断是否闰年
     *
     * @param year 年份
     * @return {@code true}: 闰年<br>{@code false}: 平年
     */
    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * 判断是否闰年
     *
     * @param date Date类型时间
     * @return {@code true}: 闰年<br>{@code false}: 平年
     */
    public static boolean isLeapYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return isLeapYear(year);
    }

    /**
     * 判断是否闰年
     *
     * @param millis 毫秒时间戳
     * @return {@code true}: 闰年<br>{@code false}: 平年
     */
    public static boolean isLeapYear(long millis) {
        return isLeapYear(millis2Date(millis));
    }


    /**
     * 相差几个自然日
     *
     * @param millisA
     * @param millisB
     * @return
     */
    public static int differDayCount(long millisA, long millisB) {
        Calendar calendarA = Calendar.getInstance();
        calendarA.setTimeInMillis(millisA);

        Calendar calendarB = Calendar.getInstance();
        calendarB.setTimeInMillis(millisB);
        return differDayCount(calendarA, calendarB);
    }

    /**
     * 相差几个自然日
     *
     * @return
     */
    public static int differDayCount(Calendar calA, Calendar calB) {
        Calendar start = calA;
        Calendar end = calB;
        if (calA.compareTo(calB) > 0) {
            start = calB;
            end = calA;
        }
        int startYear = start.get(Calendar.YEAR);
        int endYear = end.get(Calendar.YEAR);
        if (endYear == startYear) {
            return end.get(Calendar.DAY_OF_YEAR) - start.get(Calendar.DAY_OF_YEAR);
        }
        int dayCount = 0;
        for (int i = startYear; i <= endYear; i++) {
            if (i == startYear) {
                dayCount += getYearDays(i) - start.get(Calendar.DAY_OF_YEAR);
            } else if (i == endYear) {
                dayCount += end.get(Calendar.DAY_OF_YEAR);
            } else {
                dayCount += getYearDays(i);
            }
        }
        return dayCount;
    }

    public static int getYearDays(int year) {
        return isLeapYear(year) ? 366 : 365;
    }

}
