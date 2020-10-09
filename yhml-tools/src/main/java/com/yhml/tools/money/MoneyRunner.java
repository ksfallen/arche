package com.yhml.tools.money;

import com.yhml.tools.model.ToolRunner;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * 账单格式转换
 */
@Slf4j
public class MoneyRunner implements ToolRunner {

    @Override
    public void exec(List<String> args) {
        String type = CollectionUtil.isEmpty(args) ? IMoneyHandler.HANDLER_PRO : args.get(0);
        getHandler(type).process();
    }

    public IMoneyHandler getHandler(String type) {
        IMoneyHandler handler = null;
        switch (type) {
            case IMoneyHandler.HANDLER_PRO:
                handler = new MoneyProHandler();;
                break;
            case IMoneyHandler.HANDLER_ALIPAY:
                handler = new AlipayHander();
                break;
        }
        Assert.isTrue(handler != null, "MoneyRunner 转换类型错误:" + type);
        return handler;
    }

}
