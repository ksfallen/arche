# simple-config

通用配置包

[TOC]

## 更新说明

- 增加 mybatis 配置
- 增加 druid 配置
- 增加日志配置

## 集成

### druid
启用 druid 需要在主项目中加入 `druid-spring-boot-starter` 依赖
```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
</dependency>
```
[druid 默认配置](https://gitee.com/ksfallen/simple-springboot/blob/master/simple-config/src/main/resources/druid.properties)

druid.properties
- 默认路径:  `classpath:druid.properties`
- application.properties 中的配置可以覆盖 druid.properties

```
# 如  druid.properties 配置
spring.datasource.druid.initial-size=5

# 主项目中 application.properties 配置
spring.datasource.druid.initial-size=10

最终会使用 application.properties 中的配置
```

## mybaits

### 默认配置
[mybatis-config](ttps://gitee.com/ksfallen/simple-springboot/blob/master/simple-config/src/main/resources/mybatis-config.xml)

### 集成 pagehelper 分页插件
[pagehelper wiki](https://gitee.com/free/Mybatis_PageHelper)
[通用 Mapper](https://gitee.com/free/Mapper)

## 日志配置

在 application.properties中配置`logger.mapper`, 指定 mapper 包名, 打印SQL日志位
```
<springProperty scope="context" name="logger.mapper" source="logger.mapper"/>
```

## Eendpoints

### 增加 ApiEendpoint  返回所有 Api 接口
