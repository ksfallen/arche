package com.yhml.core.config.listener;

import com.yhml.core.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.web.context.support.StandardServletEnvironment;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    private int serverPort;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        doStatic();
        log.info("=======================================");
        log.info("=         Application Ready           =");
        log.info("=======================================");
        logApplicationStartup();
    }

    private void doStatic() {
        log.info("bean definition count = {}", ctx.getBeanDefinitionCount());
        if (env instanceof StandardServletEnvironment) {
            List<String> list = new ArrayList<>();
            StandardServletEnvironment senv = (StandardServletEnvironment) env;

            for (PropertySource<?> next : senv.getPropertySources()) {
                String name = ((PropertySource) next).getName();
                list.add(name);
            }
            log.info("load file = {}", StringUtil.join(list));
        }
    }

    // public void printurl() {
    //     try {
    //         int currentPort = getTomcatPort();
    //         currentPort = currentPort == 0 ? port : currentPort;
    //
    //         String url = "http://" + getIpAddress() + ":" + currentPort;
    //         log.info(url);
    //     } catch (Exception ignored) {
    //     }
    // }


    /**
     * 获取web容器的ip地址
     */
    private static String getIpAddress() {
        String hostAddress = "localhost";

        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }

        return hostAddress;
    }

    /**
     * 获取web服务端口
     */
    private static int getTomcatPort() throws MalformedObjectNameException, NullPointerException, AttributeNotFoundException, MBeanException,
            ReflectionException, InstanceNotFoundException {
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

        return Integer.parseInt(portStr);
    }

    // @formatter:off
    private void logApplicationStartup() {
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }

        // String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");

        if (StringUtils.isBlank(contextPath)) {
            contextPath = "/";
        }

        String hostAddress = "localhost";

        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }

        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\t{}://localhost:{}{}\n\t" +
                        "External: \t{}://{}:{}{}\n\t" +
                        "Profile(s): \t{}" +
                        "\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                protocol, serverPort, contextPath,
                protocol, hostAddress, serverPort, contextPath,
                env.getActiveProfiles());
    }
}
