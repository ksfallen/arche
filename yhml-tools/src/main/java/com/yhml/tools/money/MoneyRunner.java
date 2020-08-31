package com.yhml.tools.money;

import com.yhml.tools.model.ToolRunner;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * Moneypro 数据转 moneywiz
 *
 * @date 2020/5/18
 */
@Slf4j
public class MoneyRunner implements ToolRunner {

    @Override
    public void exec(List<String> args) {
        String type = CollectionUtil.isEmpty(args) ? IMoneyHandler.HANDLER_PRO : args.get(0);
        getHandler(type).porcess();
    }

    public IMoneyHandler getHandler(String type) {
        IMoneyHandler handler = null;
        if (type.equals(IMoneyHandler.HANDLER_PRO)) {
            handler = new MoneyProHandler();
        }

        if (type.equals(IMoneyHandler.HANDLER_ALIPAY)) {
            handler = new AlipayHander();
        }

        Assert.isTrue(handler != null, "MoneyRunner 转换类型错误:" + type);
        return handler;
    }

}
