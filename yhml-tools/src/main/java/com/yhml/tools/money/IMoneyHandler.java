package com.yhml.tools.money;

/**
 * @author Jfeng
 * @date 2020/8/28
 */
public interface IMoneyHandler {

    String HANDLER_PRO = "pro";
    String HANDLER_WIZ = "wiz";
    String HANDLER_ALIPAY = "alipay";

    void process();
}
