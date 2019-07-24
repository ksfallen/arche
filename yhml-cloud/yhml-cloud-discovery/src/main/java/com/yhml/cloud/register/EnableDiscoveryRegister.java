// package com.yhml.cloud.register;
//
// import java.lang.annotation.*;
//
// import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//
// /**
//  * 自动将服务注册到注册中心，使用方只需在Application上加上@EnableDiscoveryRegister注解
//  * 并且在配置文件里指定spring.application.name=${你的服务名}。
//  * 比如：spring.application.name=axe  配置文件里的这行配置，启动后将会注册一个服务名为axe的服务到注册中心
//  */
// @Target(ElementType.TYPE)
// @Retention(RetentionPolicy.RUNTIME)
// @Documented
// @Inherited
// @EnableDiscoveryClient
// public @interface EnableDiscoveryRegister {
//
// //    String[] appName() default {};
// }
