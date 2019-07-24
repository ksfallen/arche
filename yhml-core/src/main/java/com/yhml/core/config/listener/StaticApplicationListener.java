package com.yhml.core.config.listener;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;

import javax.management.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.web.context.support.StandardServletEnvironment;

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

    @Value("${server.port:8080}")
    private int port;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        doStatic();

        log.info("=======================================");
        log.info("=         Application Ready           =");
        log.info("=======================================");
    }

    private void doStatic() {
        printurl();

        log.info("bean definition count = {}", ctx.getBeanDefinitionCount());

        if (env instanceof StandardServletEnvironment) {
            StandardServletEnvironment senv = (StandardServletEnvironment) env;

            for (PropertySource<?> next : senv.getPropertySources()) {
                String name = "";

                if (next instanceof OriginTrackedMapPropertySource) {
                    name = ((OriginTrackedMapPropertySource) next).getName();
                }

                if (next instanceof ResourcePropertySource) {
                    name = ((ResourcePropertySource) next).getName();
                }

                if (name.length() > 0) {
                    log.info("load file = {}", name);
                }
            }
        }
    }

    public void printurl() {
        try {
            int currentPort = getTomcatPort();
            currentPort = currentPort == 0 ? port : currentPort;

            String url = "http://" + getIpAddress() + ":" + currentPort;
            log.info(url);
        } catch (Exception ignored) {
        }
    }


    /**
     * 获取web容器的ip地址
     */
    public static String getIpAddress() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    /**
     * 获取web服务端口
     */
    public static int getTomcatPort() throws MalformedObjectNameException, NullPointerException, AttributeNotFoundException,
            MBeanException, ReflectionException, InstanceNotFoundException {
        MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
        Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"), null);

        String portStr = "0";
        if (objectNames.size() > 0) {
            for (ObjectName objectName : objectNames) {
                String scheme = (String) beanServer.getAttribute(objectName, "protocol");

                log.info("protocol:" + scheme);

                if ("HTTP/1.1".equals(scheme) || "org.apache.coyote.http11.Http11NioProtocol".equals(scheme)) {
                    portStr = objectName.getKeyProperty("port");
                    break;
                }
            }
        }

        return Integer.valueOf(portStr);
    }

}
