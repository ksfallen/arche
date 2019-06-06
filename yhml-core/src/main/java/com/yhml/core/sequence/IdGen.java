package com.yhml.core.sequence;

import java.util.Calendar;
import java.util.Date;

import com.yhml.core.util.RedisClientUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: Jfeng
 * @date: 2019-05-14
 */
@Slf4j
public class IdGen {

    public String getIdPrefix() {
        return getIdPrefix(new Date());
    }

    public String getIdPrefix(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        String _day = String.format("%1$03d", day);
        String _hour = String.format("%1$02d", hour);
        String _min = String.format("%1$02d", min);
        String _second = String.format("%1$02d", second);

        return (year - 2000) + _day + _hour;
    }

    public Long getOrderId(String prefix) {
        try {
            Long incr = RedisClientUtil.incr(prefix);
            String orderId = "t_order_id" + prefix + String.format("%1$05d", incr);
            return Long.valueOf(orderId);
        } catch (Exception e) {
            log.error("", e);
        }

        return null;
    }

    public Long mix(Long value) {
        return value ^ Integer.MAX_VALUE;
    }

    public static void main(String[] args) {
        String prefix = new IdGen().getIdPrefix();
        Long aLong = Long.valueOf(prefix);

        System.out.println(String.format("%1$05d", 1000000));
        System.out.println(prefix);
        System.out.println(aLong);
        System.out.println(aLong ^ Integer.MAX_VALUE);
        System.out.println((aLong ^ Integer.MAX_VALUE) ^ Integer.MAX_VALUE);
    }
}

