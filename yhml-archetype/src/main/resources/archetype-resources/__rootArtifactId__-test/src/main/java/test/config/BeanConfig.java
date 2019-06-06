import java.util.Locale;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.util.IntrospectorCleanupListener;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

import lombok.extern.slf4j.Slf4j;

#set($symbol_pound='#')
        #set($symbol_dollar='$')
        #set($symbol_escape='\' )

        package ${package}.test.config;

/**
 * 模块名
 * @author bins
 */
@Slf4j
@Component
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


}
