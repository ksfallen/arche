package com.yhml.core.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static final ZoneOffset GMT_8 = ZoneOffset.ofHours(8);

    public static void main(String[] args) {
        System.out.println("Instant.now() = " + Instant.now());
        System.out.println(System.currentTimeMillis() / 1000L);
        System.out.println(Instant.now().getEpochSecond());

        System.out.println(LocalDate.now().minusDays(1).getDayOfWeek().getValue());
        System.out.println("getWeek() = " + getWeek());
        System.out.println("getDiffTimeStr(DateUtil.date(), DateUtil.offsetWeek(DateUtil.date(), 1)) = " + getDiffTimeStr(DateUtil.date(),
                DateUtil.offsetWeek(DateUtil
                .date(), 1)));
        System.out.println(DateUtil.formatBetween(DateUtil.date(), DateUtil.offsetWeek(DateUtil.date(), 1)));
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atOffset(GMT_8).toLocalDateTime();
    }

    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.toInstant(GMT_8));
    }

    public static Date toDate(LocalDate localDate) {
        return toDate(localDate.atStartOfDay());
    }

    /**
     * 10 位时间错 秒
     */
    public static Long toTimestamp() {
        return Instant.now().getEpochSecond();
    }

    public static long toTimestamp(Date date) {
        return date == null ? 0 : toLocalDateTime(date).toInstant(GMT_8).getEpochSecond();
    }

    /**
     * 转时间戳
     * 支持  yyyy-MM-dd yyyy-MM-dd HH:mm:ss
     */
    public static Long toTimestamp(String dateString) {
        return toTimestamp(parse(dateString, DatePattern.NORM_DATE_PATTERN, DatePattern.NORM_DATETIME_PATTERN));
    }

    public static Long toTimestamp(String dateString, String... pattern) {
        return toTimestamp(parse(dateString, pattern));
    }


    public static String nowDateTime() {
        return DateUtil.now();
    }

    public static String nowDate() {
        return LocalDate.now().toString();
    }

    public static String getCurrentTime(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将字符串转换成Date类型
     */
    public static Date parse(String dateString, String... pattern) {
        if (ArrayUtil.isEmpty(pattern)) {
            return null;
        }

        try {
            return parseDate(dateString, pattern);
        } catch (ParseException e) {
            log.error("", e);
        }

        return null;
    }


    /**
     * 将Date类型转化成字符串
     */
    public static String format(Date date, String format) {
        return date == null ? null : DateUtil.format(date, format);
    }


    /**
     * 在传入的日期基础上往后加n天
     *
     * @param n 要加的天数
     */
    public static Date addDay(Date date, int n) {
        return DateUtil.offsetDay(date, n);
    }

    public static Date backDay(Date date, int n) {
        return addDay(date, -n);
    }

    //

    /**
     * 判断当前时间是否在开始时间与结束时间之间
     *
     * @param time  当前时间
     * @param begin 开始时间
     * @param end   结束时间
     * @return boolen类型，true表示在两者间，false表示不在两者之间
     */
    public static boolean isTimeIn(Date time, Date begin, Date end) {
        return time.getTime() >= begin.getTime() && time.getTime() <= end.getTime();
    }
    //

    /**
     * 判断指定日期是星期几
     *
     * @return 返回数字[1:星期一，2：星期二，....，7：星期日]
     */
    public static int getWeek(String dateTimeStr, String pattern) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateTimeStr, df).getDayOfWeek().getValue();
    }
    //

    /**
     * 判断指定日期是星期几
     *
     * @return 返回数字[1:星期一，2：星期二，....，7：星期日]
     */
    public static int getWeek(Date date) {
        return toLocalDateTime(date).getDayOfWeek().getValue();
    }

    public static int getWeek() {
        return LocalDate.now().getDayOfWeek().getValue();
    }


    /**
     * 判断是否为有效的身份证日期
     */
    public static boolean isIdDate(String date) {
        if (StringUtil.isEmpty(date)) {
            return false;
        }

        date = date.trim();

        if (date.length() != DatePattern.PURE_DATE_PATTERN.length()) {
            return false;
        }

        Optional<Date> optional = Optional.ofNullable(parse(date, DatePattern.PURE_DATE_PATTERN));

        return optional.isPresent() && StringUtil.equals(date, format(optional.get(), DatePattern.PURE_DATE_PATTERN));
    }

    /**
     * 将字符串日期转成Timestamp类型
     *
     * @param dateString 字符串类型的时间
     * @param format     字符串类型的时间要转换的格式
     * @return Timestamp类型的时间戳
     */
    public static java.sql.Timestamp parse2Timestamp(String dateString, String format) {
        return new java.sql.Timestamp(DateUtil.parse(dateString, format).getTime());
    }


    /**
     * 获取两个时间的间隔,字符串表示
     */
    public static String getDiffTimeStr(Date start, Date end) {
        String time = "";
        if (start != null && end != null) {
            int t = (int) (end.getTime() - start.getTime()) / 1000;
            String h = "";
            String m = "";
            String s = "";
            h = (int) t / 3600 + "";
            m = (int) (t % 3600) / 60 + "";
            s = t % 60 + "";
            if (h.length() <= 1) {
                h = "0" + h;
            }
            if (m.length() <= 1) {
                m = "0" + m;
            }
            if (s.length() <= 1) {
                s = "0" + s;
            }
            time = h + ":" + m + ":" + s;
        }
        return time;
    }
    //
    // /**
    //  * 获取两个日期之间间隔的分钟数
    //  *
    //  * @param startDate
    //  * @param endDate
    //  * @return
    //  */
    // public static int getIntervalMinute(Date startDate, Date endDate) {
    //     int min = 0;
    //     if (null != startDate && null != endDate) {
    //         long end = endDate.getTime();
    //         long start = startDate.getTime();
    //         long betweenDate = (end - start) / (60 * 1000);
    //         min = Long.valueOf(betweenDate).intValue();
    //     }
    //     return min;
    // }
    //
    // /**
    //  * 获取两个日期之间间隔的天数
    //  *
    //  * @param startDate
    //  * @param endDate
    //  * @return
    //  * @author sunyy
    //  */
    // public static int getIntervalDay(Date startDate, Date endDate) {
    //     int day = 0;
    //     if (null != startDate && null != endDate) {
    //         long end = endDate.getTime();
    //         long start = startDate.getTime();
    //         long betweenDate = (end - start) / (24 * 60 * 60 * 1000);
    //         day = Long.valueOf(betweenDate).intValue();
    //     }
    //
    //     return day;
    // }
    //
    // /**
    //  * 根据类型参数返回不同的日期
    //  *
    //  * @param type <pre>
    //  *                          today:当天
    //  *                          yesterday：前一天
    //  *                          less7：前6天 （近7天）
    //  *                          less30：前29天 （近30天）
    //  *                          all：前29年 （近30年）
    //  *                         </pre>
    //  * @return 返回yyyy-MM-dd格式字符串
    //  * @author zhouzc
    //  */
    // public static String getSpecifiedDay(String type) {
    //     String time = "";
    //     if ("today".equals(type)) {
    //         time = getCurrentTime(DATE_FORMAT);
    //     } else if ("yesterday".equals(type)) {
    //         Date yesterday = addDay(new Date(), -1);
    //         time = format(yesterday, DATE_FORMAT);
    //     } else if ("less7".equals(type)) {
    //         Date yesterday = addDay(new Date(), -6);
    //         time = format(yesterday, DATE_FORMAT);
    //     } else if ("less30".equals(type)) {
    //         Date yesterday = addDay(new Date(), -29);
    //         time = format(yesterday, DATE_FORMAT);
    //     } else if ("all".equals(type)) {
    //         // 取全部就设置截至时间为30年以前
    //         Date yesterday = addYears(new Date(), -29);
    //         time = format(yesterday, DATE_FORMAT);
    //     }
    //     return time;
    // }
    //
    //
    // /**
    //  * 是否为工作日
    //  */
    // public static boolean isWorkingDay(Date date) {
    //     List<HolidayUtil.Holiday> holidayList = HolidayUtil.getInstance().getHolidayList();
    //     int ldate = Integer.parseInt(format(date, DATE_FORMAT_NO_DELIMITER));
    //
    //     // 是否为国定假日
    //     for (HolidayUtil.Holiday holiday : holidayList) {
    //         int start = new Integer(holiday.getStartTime());
    //         int end = new Integer(holiday.getEndTime());
    //         if (ldate >= start && ldate <= end) {
    //             return false;
    //         }
    //     }
    //
    //     Calendar hcal = Calendar.getInstance();
    //     hcal.setTime(date);
    //
    //     // 周末
    //     return hcal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && hcal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY;
    // }
}
