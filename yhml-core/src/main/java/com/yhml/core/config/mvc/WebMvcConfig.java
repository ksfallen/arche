package com.yhml.core.config.mvc;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


// @Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        // registry.addInterceptor(new LoggerInterceptor());
        //.addPathPatterns("/**");
    }

    /**
     * 配置静态访问资源
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // addResoureHandler指的是对外暴露的访问路径
        // addResourceLocations指的是文件放置的目录
        // registry.addResourceHandler("/my/**").addResourceLocations("classpath:/my/");

        // 指定外部的目录
        // registry.addResourceHandler("/my/**").addResourceLocations("file:E:/my/");

    }

    /**
     * 页面跳转addViewControllers

     * 以前要访问一个页面需要先创建个Controller控制类，再写方法跳转到页面
     * 在这里配置后就不需要那么麻烦了，直接访问http://localhost:8080/toLogin就跳转到login.jsp页面了
     * @param registry
     */
    // @Override
    // public void addViewControllers(ViewControllerRegistry registry) {
    //     registry.addViewController("/toLogin").setViewName("login");
    //     super.addViewControllers(registry);
    // }
}
