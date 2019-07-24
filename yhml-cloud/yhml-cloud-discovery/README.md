## 服务自动注册

### 1 引入依赖
``` xml
  <dependency>
      <groupId>com.yhml</groupId>
      <artifactId>yhml-cloud</artifactId>
      <version>1.0.0</version>
  </dependency>
```

### 2 增加注解
``` java
@EnableDiscoveryClient
public class Application {}
```

### 4 配置文件
``` properties
spring.application.name=xxx
```
            
        
        
