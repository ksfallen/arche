package com.yhml.tools;

import com.yhml.tools.model.ToolEnum;
import com.yhml.tools.model.ToolRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;

/**
 * @author Jfeng
 * @date 2020/5/20
 */
public class Tools {

    public static final String JAR_NAME = "yhml-tools";

    public static void main(String[] args) throws Exception {
        if (args == null || args.length == 0) {
            System.out.println("[#] 缺少参数: " + Arrays.toString(args));
            return;
        }
        List<String> list = new ArrayList<>();
        if  (args.length > 1) {
            String[] sub = ArrayUtil.sub(args, 1, args.length);
            list = CollectionUtil.toList(sub);
        }

        Object object = ToolEnum.valueOf(args[0]).getType().newInstance();
        ToolRunner runner = (ToolRunner) object;
        runner.exec(list);
    }
}
