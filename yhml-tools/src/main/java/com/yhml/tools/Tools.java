package com.yhml.tools;

import com.yhml.tools.model.ToolEnum;
import com.yhml.tools.model.ToolRunner;
import org.junit.Test;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONUtil;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Jfeng
 * @date 2020/5/20
 */
@Slf4j
public class Tools {

    public static final String JAR_NAME = "yhml-tools";

    @Test
    public void test() {
        // String[] args = {ToolEnum.money.name()};
        String[] args = {ToolEnum.ssr.name()};
        Tools.main(args);
    }

    public static void main(String[] args) {
        if (ArrayUtil.isEmpty(args)) {
            log.info("缺少参数: {}" , JSONUtil.toJsonStr(args));
            return;
        }

        List<String> list = new ArrayList<>(1);

        if (args.length > 1) {
            String[] sub = ArrayUtil.sub(args, 1, args.length);
            list = CollectionUtil.toList(sub);
        }

        Object object = ReflectUtil.newInstance(ToolEnum.valueOf(args[0]).getType());
        ((ToolRunner) object).exec(list);
    }
}
