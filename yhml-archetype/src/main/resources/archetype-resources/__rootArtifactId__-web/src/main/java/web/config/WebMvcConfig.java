import javax.validation.Validator;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

#set($symbol_pound='#')
        #set($symbol_dollar='$')
        #set($symbol_escape='\' ) package ${package}.web.config;
        {package}.web.interceptor.RequestContextInterceptor;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public Validator validator(MessageSource messageSource) {
        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
        factory.setValidationMessageSource(messageSource);
        return factory;
    }

    @Bean
    public HandlerInterceptor getLocaleChangeInterceptor() {
        return new LocaleChangeInterceptor();
    }

    @Bean
    public RequestContextInterceptor getRequestContextInterceptor() {
        return new RequestContextInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);

        //TODO 自定义拦截器
        registry.addInterceptor(getLocaleChangeInterceptor());

//        registry.addInterceptor(getRequestContextInterceptor())
//                .addPathPatterns("/api/**")
//                .excludePathPatterns("/api/sys/config");

        //TODO
    }
}
