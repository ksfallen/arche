package com.java8;

import org.junit.Test;

import com.yhml.core.base.bean.Result;

import cn.hutool.core.bean.BeanPath;

/**
 * @author: Jfeng
 * @date: 2019-07-16
 */
public class BeanTest {


    @Test
    public void test() {
        Result result = Result.ok();
        BeanPath path = BeanPath.create("result.msg");
        System.out.println(path.get(result));

        Integer num = -1;
        System.out.println(-num);
    }
}
