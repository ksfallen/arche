package com.yhml.cloud.register;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;

import javax.management.*;

import lombok.extern.slf4j.Slf4j;

/**
 * 用于获取web应用的IP地址和端口.
 */
@Slf4j
public class IpAddressKowalski {

    /**
     * 获取web容器的ip地址
     */
    public static String getIpAddress() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    /**
     * 获取web服务端口
     */
    public static int getTomcatPort() throws MalformedObjectNameException, NullPointerException, AttributeNotFoundException, MBeanException, ReflectionException, InstanceNotFoundException {
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
