package com.yhml.cloud.register;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationAutoConfiguration;
import org.springframework.cloud.zookeeper.discovery.ZookeeperDiscoveryAutoConfiguration;
import org.springframework.cloud.zookeeper.discovery.ZookeeperDiscoveryProperties;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperServiceRegistryAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@AutoConfigureBefore({ZookeeperServiceRegistryAutoConfiguration.class, AutoServiceRegistrationAutoConfiguration.class,
        ZookeeperDiscoveryAutoConfiguration.class})
public class DiscoveryRegisterConfiguration {

    @Value("${spring.application.name}")
    private String application;

    @Value("${server.port:8080}")
    private int port;

    @Value("${spring.profiles.active:dev}")
    private String root;

    @Autowired
    private ZookeeperDiscoveryProperties properties;

    @PostConstruct
    public ZookeeperDiscoveryProperties zookeeperDiscoveryProperties() throws Exception {
        int currentPort = IpAddressKowalski.getTomcatPort();
        currentPort = currentPort == 0 ? port : currentPort;

        Map<String, String> metadata = new HashMap<>();
        metadata.put("application", application);

        properties.setInstancePort(currentPort);
        properties.setPreferIpAddress(true);
        properties.setMetadata(metadata);

        if ("/services".equals(properties.getRoot())) {
            properties.setRoot(root);
        }

        log.info(properties.toString());

        return properties;
    }


}
