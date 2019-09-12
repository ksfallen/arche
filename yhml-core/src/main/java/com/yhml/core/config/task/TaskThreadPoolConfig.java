package com.yhml.core.config.task;

import lombok.Data;

// @Component
// @ConfigurationProperties(prefix ="spring.task.pool", ignoreInvalidFields = true)
@Data
public class TaskThreadPoolConfig {
    private int corePoolSize = 10;
    private int maxPoolSize = 200;
    private int keepAliveSeconds = 60;
    private int queueCapacity = 10;

}
