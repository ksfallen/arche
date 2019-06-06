package com.yhml.core.sequence;

import org.apache.commons.lang3.StringUtils;


/**
 * 汇金平台全局通用ID生成器
 * 支持如下特性:
 * 1、纯粹的分布式ID
 * 2、带业务上下文的分布式 ID
 * APPID|领域对象编号|业务类型|日期               |唯一标识符
 * 每段的最大长度为：
 * 2字符       8字符              2字符        6字符(可选) 18字符
 * 30字符或者36字符
 * 唯一标识符采用snowflake算法
 *
 * @author admin
 */
public class UniqueIdGen {
    /**
     * @param appId    汇金平台定义的传化应用唯一标识符
     * @param objectId 对象ID, 一般为具体账户的编号, 推荐使用partyId或centerAccount
     * @param bizType  汇金平台定义的传化应用唯一业务类型
     * @param date     日期, YYYYMMDD格式
     * @return 40位标识符
     */
    public static String getUniqueId(String appId, String objectId, String bizType, String date) {
        return StringUtils.leftPad(appId, 4, '0').substring(0, 4) + StringUtils.leftPad(objectId, 8, '0').substring(0, 8) + StringUtils
                .leftPad(bizType, 4, '0').substring(0, 4) + StringUtils.leftPad(date, 8, '0').substring(2, 8) + SnowflakeWorker.getId();
    }

    /**
     * @param appId    汇金平台定义的传化应用唯一标识符
     * @param objectId 对象ID, 一般为具体账户的编号, 推荐使用partyId或centerAccount
     * @param bizType  汇金平台定义的传化应用唯一业务类型
     * @return 40位标识符
     */
    public static String getUniqueIdWithSysdate(String appId, String objectId, String bizType) {
        return StringUtils.leftPad(appId, 4, '0').substring(0, 4) + StringUtils.leftPad(objectId, 8, '0').substring(0, 8) + StringUtils
                .leftPad(bizType, 4, '0').substring(0, 4) + SnowflakeWorker.getId();
    }

    /**
     * 汇金平台内部流水
     *
     * @param appId    汇金平台定义的传化应用唯一标识符
     * @param objectId 对象ID, 一般为具体账户的编号, 推荐使用partyId或centerAccount
     * @param bizType  汇金平台定义的传化应用唯一业务类型
     * @return 34位标识符
     */
    public static String getUniqueId(String appId, String objectId, String bizType) {
        return StringUtils.leftPad(appId, 4, '0').substring(0, 4) + StringUtils.leftPad(objectId, 8, '0').substring(0, 8) +
                // StringUtils.leftPad(bizType, 4, '0').substring(0, 4) +
                SnowflakeWorker.getId();
    }

    /**
     * @param objectId 对象ID, 一般为具体账户的编号, 推荐使用partyId或centerAccount
     * @return 26位标识符
     */
    public static String getUniqueId(String objectId) {
        return StringUtils.leftPad(objectId, 8, '0').substring(0, 8) + SnowflakeWorker.getId();
    }

    /**
     * @param appId   汇金平台定义的传化应用唯一标识符
     * @param bizType 汇金平台定义的传化应用唯一业务类型
     * @return 26位标识符
     */
    public static String getUniqueId(String appId, String bizType) {
        return StringUtils.leftPad(appId, 4, '0').substring(0, 4) + StringUtils.leftPad(bizType, 4, '0').substring(0, 4) +
                SnowflakeWorker.getId();
    }

    /**
     * 订单类推荐使用该ID生成器
     *
     * @param appId   汇金平台定义的传化应用唯一标识符
     * @param bizType 汇金平台定义的传化应用唯一业务类型
     * @return 32位标识符
     */
    public static String getUniqueIdWithSysdate(String appId, String bizType) {
        return StringUtils.leftPad(appId, 4, '0').substring(0, 4) + StringUtils.leftPad(bizType, 4, '0').substring(0, 4) +
                // DateUtils.SDF_SHORT_DATE_NUM.format(new Date()) +
                SnowflakeWorker.getId();
    }

    /**
     * @return 18位数字标识符
     */
    public static String getUniqueId() {
        return String.valueOf(SnowflakeWorker.getId());
    }

    public static void main(String[] args) {
        System.out.println(UniqueIdGen.getUniqueId());
        System.out.println(UniqueIdGen.getUniqueId("1000"));
        System.out.println(UniqueIdGen.getUniqueId("01", "OD"));
        System.out.println(UniqueIdGen.getUniqueIdWithSysdate("01", "OD"));
        System.out.println(UniqueIdGen.getUniqueId("01", "1000", "OD"));
        // System.out.println(UniqueIdGen.getUniqueId("01","1000", "OD",DateUtils.SDF_SHORT_DATE_NUM.format(new Date())));
        System.out.println(UniqueIdGen.getUniqueIdWithSysdate("01", "1000", "OD"));
    }
}
