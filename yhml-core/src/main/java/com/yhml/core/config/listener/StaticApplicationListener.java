package com.yhml.core.config.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.env.Environment;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: Jfeng
 * @date: 2019-06-26
 */
@Slf4j
public class StaticApplicationListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private AbstractApplicationContext ctx;

    @Autowired
    private Environment env;

    public static StaticApplicationListener build() {
        return new StaticApplicationListener();
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        doStatic();

        log.info("=======================================");
        log.info("=         Application Ready           =");
        log.info("=======================================");

        // doCopyRight();
    }

    private void doStatic() {
        log.info("bean definition count = {}", ctx.getBeanDefinitionCount());

        // if (env instanceof StandardServletEnvironment) {
        //     StandardServletEnvironment senv = (StandardServletEnvironment) env;
        //
        //     for (PropertySource<?> next : senv.getPropertySources()) {
        //         String name = "";
        //
        //         if (next instanceof OriginTrackedMapPropertySource) {
        //             name = ((OriginTrackedMapPropertySource) next).getName();
        //         }
        //
        //         if (next instanceof ResourcePropertySource ) {
        //             name = ((ResourcePropertySource) next).getName();
        //         }
        //
        //         if (name.length() > 0) {
        //             log.info("load file = {}", name);
        //         }
        //     }
        // }
    }

    private void doCopyRight() {
        log.info("Power by Jfeng");
    }

}
