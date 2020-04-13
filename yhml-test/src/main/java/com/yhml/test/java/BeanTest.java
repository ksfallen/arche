package com.yhml.test.java;

import com.yhml.core.base.bean.Result;
import org.junit.Test;

import cn.hutool.core.bean.BeanPath;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

/**
 * @author: Jfeng
 * @date: 2019-07-16
 */
public class BeanTest {

    @Test
    public void time() {
        /**
         * JDBC映射将把数据库的日期类型
         * date -> LocalDate
         * time -> LocalTime
         * timestamp -> LocalDateTime
         */
        System.out.println(System.currentTimeMillis());
        System.out.println(Clock.systemDefaultZone().millis());
        System.out.println(Clock.systemUTC().millis());
        System.out.println(Clock.systemUTC().instant());
        System.out.println(Clock.systemDefaultZone().instant());

        System.out.println("====================");
        System.out.println(LocalDate.now());
        System.out.println(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()));
        System.out.println(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()));
        System.out.println(LocalDate.now().with(TemporalAdjusters.dayOfWeekInMonth(1, DayOfWeek.SUNDAY)));
        System.out.println(LocalDate.now().with(TemporalAdjusters.dayOfWeekInMonth(2, DayOfWeek.MONDAY)));
        System.out.println(LocalTime.now());
        System.out.println(LocalTime.now().withNano(0));
        System.out.println(LocalDateTime.now());
        System.out.println(LocalDateTime.of(2019,10,1,10,12,12));

        System.out.println("====================");
        System.out.println(ZoneId.systemDefault());

        //Duration 此类用来计算两同类型日期的时间差
        System.out.println("====================");
        LocalDateTime start = LocalDateTime.of(2017, 1, 1, 1, 1);
        LocalDateTime end = LocalDateTime.of(2017, 2, 1, 1, 1);
        Duration result = Duration.between(start, end);
        System.out.println(result.toDays()); //31
        System.out.println(result.toHours()); //744
        System.out.println(result.toMinutes()); //44640
        System.out.println(result.toMillis()); //2678400000
        System.out.println(result.toNanos()); //2678400000000000

        /**
         * G 年代标志符
         * y 年
         * M 月
         * d 日
         * h 时 (12小时制)
         * H 时 (24小时制)
         * m 分
         * s 秒
         * S 毫秒
         * E 星期几
         * D 一年中的第几天
         * F 一月中第几个星期(以每个月1号为第一周,8号为第二周为标准计算)
         * w 一年中第几个星期
         * W 一月中第几个星期(不同于F的计算标准,是以星期为标准计算星期数,例如1号是星期三,是当月的第一周,那么5号为星期日就已经是当月的第二周了)
         * a 上午 / 下午 标记符
         * k 时 (24小时制,其值与H的不同点在于,当数值小于10时,前面不会有0)
         * K 时 (12小时值,其值与h的不同点在于,当数值小于10时,前面不会有0)
         * z 时区
         */
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("G yyyy年MM月dd号 E a hh时mm分ss秒")));
    }


    @Test
    public void test() {
        Result result = Result.ok();
        BeanPath path = BeanPath.create("result.msg");
        System.out.println(path.get(result));
    }


    @Test
    public void reflectTest() {
        Method[] methods = BeanTest.class.getMethods();
        Parameter[] parameters = methods[0].getParameters();
        Arrays.stream(parameters).forEach(parameter -> System.out.println(parameter.getName()));
    }

    @Test
    public void async() throws ExecutionException, InterruptedException {
        CompletableFuture cf = CompletableFuture.completedFuture("message");
        assertTrue(cf.isDone());
        assertEquals("message", cf.getNow(null));
        System.out.println(cf.get());
    }

    @Test
    public void optional() {
        Object o = Optional.empty().orElse(null);
        System.out.println("o = " + o);
    }

}
