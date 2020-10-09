package com.yhml.core.config.task;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@ConfigurationProperties(prefix ="spring.task.pool", ignoreInvalidFields = true)
public class TaskThreadPoolConfig {
    private int corePoolSize = 5;
    private int maxPoolSize = 5;
    private int keepAliveSeconds = 60;
    private int queueCapacity = 10;

}
