#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\' )
package ${package}.web.config;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.util.IntrospectorCleanupListener;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

import lombok.extern.slf4j.Slf4j;



@Component
@Slf4j
public class BeanConfig {

    /**
     * 注入FastJSON
     * @return
     */
    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat,
                SerializerFeature.QuoteFieldNames,
                SerializerFeature.IgnoreErrorGetter
        );
        fastConverter.setFastJsonConfig(fastJsonConfig);
        return fastConverter;
    }


    @Bean
    public ServletListenerRegistrationBean sysListener() {
        ServletListenerRegistrationBean registrationBean = new ServletListenerRegistrationBean();
        IntrospectorCleanupListener sysListener = new IntrospectorCleanupListener();
        registrationBean.setListener(sysListener);

        registrationBean.setOrder(1);

        return registrationBean;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Bean
    public CookieLocaleResolver localResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(Locale.CHINA);
        return resolver;
    }

    @Value("${symbol_dollar}{task.${parentArtifactId}.task-core-pool-size:10}")
    private Integer taskCorePoolSize;

    @Value("${symbol_dollar}{task.${parentArtifactId}.task-max-pool-size:200}")
    private Integer taskMaxPoolSize;

    @Value("${symbol_dollar}{task.${parentArtifactId}.task-queue-capacity:100}")
    private Integer taskQueueCapacity;

    /**
     * 计划任务线程池配置
     * @return
     */
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(taskCorePoolSize);
        executor.setMaxPoolSize(taskMaxPoolSize);
        executor.setQueueCapacity(taskQueueCapacity);
        executor.setThreadNamePrefix("${parentArtifactId}-TaskExecutor-");

        return executor;
    }

    @Value("${symbol_dollar}{task.${parentArtifactId}.async-task-concurrency-limit:100}")
    private Integer asyncTaskConcurrencyLimit;

    /**
     * @return
     */
    @Bean
    public SimpleAsyncTaskExecutor simpleAsyncTaskExecutor() {
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();
        executor.setConcurrencyLimit(asyncTaskConcurrencyLimit);
        executor.setThreadNamePrefix("${parentArtifactId}-AsyncTaskExecutor-");
        return executor;
    }
}
