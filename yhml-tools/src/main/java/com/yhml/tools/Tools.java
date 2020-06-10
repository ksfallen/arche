package com.yhml.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.yhml.tools.model.ToolEnum;
import com.yhml.tools.model.ToolRunner;
import org.junit.Test;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;

/**
 * @author Jfeng
 * @date 2020/5/20
 */
public class Tools {

    public static final String JAR_NAME = "yhml-tools";

    @Test
    public void test() {
        String[] args = {ToolEnum.money.name()};
        // String[] args = {ToolEnum.ssr.name()};
        Tools.main(args);
    }

    public static void main(String[] args) {
        if (ArrayUtil.isEmpty(args)) {
            System.out.println("[#] 缺少参数: " + Arrays.toString(args));
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
