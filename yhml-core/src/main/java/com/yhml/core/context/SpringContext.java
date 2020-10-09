package com.yhml.core.context;

import com.yhml.core.annotaton.ServiceSelector;
import com.yhml.core.util.StringUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.OrderComparator;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContext.applicationContext = applicationContext;
        log.info(">>>>>>>>>>>>>>> {} load success", this.getClass().getName());

        //  缓存 ServiceSelector 注解的Bean
        Map<String, Object> map = applicationContext.getBeansWithAnnotation(ServiceSelector.class);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            ServiceSelector selector = entry.getValue().getClass().getAnnotation(ServiceSelector.class);
            if (selector == null && StringUtil.isBlank(selector.key())) {
                continue;
            }
            SimpleServiceHandler.addBean(selector.key() + selector.subKey(), entry.getValue());
        }
    }

    /**
     * 获取 applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static boolean containsBean(String beanName) {
        return applicationContext.containsBean(beanName);
    }

    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }


    public static <T> T getBean(Class<T> clazz) {
        try {
            return applicationContext.getBean(clazz);
        } catch (BeansException ex) {
            Map<String, T> map = getBeansOfType(clazz);
            Optional<Map.Entry<String, T>> any = map.entrySet().stream().filter(e -> e.getValue().getClass().equals(clazz)).findAny();
            return (T) any.orElse(null);
        }
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        return applicationContext.getBean(beanName, clazz);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        return applicationContext.getBeansOfType(type);
    }

    public static String[] getBeanNamesForType(Class<?> type) {
        return applicationContext.getBeanNamesForType(type);
    }

    public static String getProperty(String key) {
        return applicationContext.getEnvironment().getProperty(key);
    }

    public static String[] getActiveProfiles() {
        return applicationContext.getEnvironment().getActiveProfiles();
    }

    public static String getActiveProfile() {
        String[] activeProfiles = getActiveProfiles();
        return ArrayUtil.isNotEmpty(activeProfiles) ? activeProfiles[0] : null;
    }


    /**
     * 根据order顺序排列
     */
    public static <T> List<T> getOrderedBeans(Class<T> clazz) {
        return getBeansOfType(clazz).values().stream().sorted(OrderComparator.INSTANCE).collect(Collectors.toList());
    }

    /**
     * 获取当前环境中,所有RestController提供的接口
     */
    public static void extractMethodMappings(Map<String, Object> result) {
        if (applicationContext != null) {
            for (Object o : applicationContext.getBeansOfType(AbstractHandlerMethodMapping.class).entrySet()) {
                Map.Entry<String, AbstractHandlerMethodMapping> bean = (Map.Entry) o;

                // 只处理 RequestMappingHandlerMapping
                if (!bean.getValue().getClass().equals(RequestMappingHandlerMapping.class)) {
                    continue;
                }

                Map<?, HandlerMethod> methods = ((AbstractHandlerMethodMapping) bean.getValue()).getHandlerMethods();

                for (Map.Entry<?, HandlerMethod> methodEntry : methods.entrySet()) {

                    HandlerMethod method = methodEntry.getValue();

                    // 只处理 restController 注解的接口
                    RestController restController = method.getBeanType().getAnnotation(RestController.class);
                    if (restController == null) {
                        continue;
                    }

                    RequestMappingInfo key = (RequestMappingInfo) methodEntry.getKey();

                    Map<String, String> map = new LinkedHashMap();
                    map.put("beanType", method.getBeanType().getName());
                    map.put("method", method.getMethod().getName());
                    map.put("url", key.getPatternsCondition().toString());
                    result.put(methodEntry.getKey().toString(), map);
                }
            }
        }
    }

    /**
     * 动态注册Bean
     *
     * @param calzz      类型
     * @param properties Bean的属性值
     */
    public static void registerBeanDefinition(Class calzz, Map<String, Object> properties) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(calzz).setLazyInit(false);
        if (CollectionUtil.isNotEmpty(properties)) {
            properties.forEach((k, v) -> builder.addPropertyValue(k, v));
        }
        // builder.setInitMethodName("start")
        // builder.setDestroyMethodName("shutdown");
        ((GenericWebApplicationContext) applicationContext).registerBeanDefinition(calzz.getName(), builder.getBeanDefinition());
        log.info("==> 注册Bean, {} ", calzz.getName());
    }


    // @SuppressWarnings("deprecation")
    // public static Method getHandleMethod(HttpServletRequest request, Class clazz) throws Exception {
    //     MethodNameResolver methodNameResolver = new InternalPathMethodNameResolver();
    //     String methodName = "/" + methodNameResolver.getHandlerMethodName(request);
    //
    //     // restful 请求风格的URL
    //     Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    //
    //     Optional<Method> method = Stream.of(clazz.getMethods()).filter(m -> m.getAnnotation(RequestMapping.class) != null).map(m -> {
    //         RequestMapping rm = m.getAnnotation(RequestMapping.class);
    //
    //         // 过滤出匹配的 methodName
    //         // Optional<String> result = Stream.of(rm.value()).filter(val -> "".equals(val) || methodName.equals(val)).findAny();
    //         boolean result = Arrays.stream(rm.value()).allMatch(val -> "".equals(val) || methodName.equals(val));
    //
    //         // methodName 存在返回 method 对象
    //         return result ? m : null;
    //     }).findAny();
    //
    //     return method.orElse(null);
    //
    // }

}
