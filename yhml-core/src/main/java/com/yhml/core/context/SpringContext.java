package com.yhml.core.context;

import java.util.*;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.OrderComparator;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext appContext = null;

    static {
        // 设置XServer模式, 该模式下，系统缺少了显示设备、键盘或鼠标
        System.setProperty("java.awt.headless", "true");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appContext = applicationContext;
        log.info(">>>>>>>>>>>>>>> {} load success", this.getClass().getName());

        // 缓存 某个类的 实现类
        // Map<String, Diversion> map = applicationContext.getBeansOfType(Diversion.class);
        // for (Diversion diversionModule : map.values()) {
        //     DiversionFactory.put(diversionModule.getDivtype(), diversionModule);
        // }
    }

    /**
     * 获取 applicationContext
     */
    public static ApplicationContext getAppContext() {
        return appContext;
    }

    public static boolean containsBean(String beanName) {
        return appContext != null && appContext.containsBean(beanName);
    }

    public static Object getBean(String beanName) {
        return appContext != null ? appContext.getBean(beanName) : null;
    }

    public static List<String> getBeanNamesForAnnotation(Class annotation) {
        String[] ss = appContext.getBeanNamesForAnnotation(annotation);
        return Arrays.asList(ss);
    }

    public static <T> T getBean(Class<T> clazz) {
        try {
            return appContext.getBean(clazz);
        } catch (BeansException ex) {
            Map<String, T> map = getBeansOfType(clazz);
            Optional<Map.Entry<String, T>> any = map.entrySet().stream().filter(e -> e.getValue().getClass().equals(clazz)).findAny();
            return (T) any.orElse(null);
        }

        // if (appContext != null) {
        //     try {
        //         return appContext.getBean(clazz);
        //     } catch (BeansException e) {
        //         Map<String, T> map = getBeansOfType(clazz);
        //         for (Map.Entry<String, T> entry : map.entrySet()) {
        //             if (entry.getValue().getClass().equals(clazz)) {
        //                 return entry.getValue();
        //             }
        //         }
        //     }
        // }
        // return null;

    }

    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) throws BeansException {
        Map<String, T> map = appContext.getBeansOfType(clazz);

        if (map.isEmpty() && appContext.getParent() != null) {
            map = appContext.getParent().getBeansOfType(clazz);
        }

        return map;
    }

    public static <T> T getBean(String beanId, Class<T> clazz) {
        return appContext != null ? appContext.getBean(beanId, clazz) : null;
    }


    /**
     * 根据order顺序排列
     */
    public static <T> List<T> getOrderedBeans(Class<T> clazz) {
        Collection<T> values = getBeansOfType(clazz).values();
        List<T> list = new ArrayList<>(values);
        OrderComparator.sort(list);
        return list;
    }

    /**
     * 获取当前环境中,所有RestController提供的接口
     *
     * @param applicationContext
     * @param result
     */
    public static void extractMethodMappings(Map<String, Object> result) {
        if (appContext != null) {

            for (Object o : appContext.getBeansOfType(AbstractHandlerMethodMapping.class).entrySet()) {
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
                    map.put("method",method.getMethod().getName()   );
                    map.put("url", key.getPatternsCondition().toString());
                    result.put(methodEntry.getKey().toString(), map);
                }
            }
        }
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
    //     // for (Method method : clazz.getMethods()) {
    //     // 	RequestMapping rm = method.getAnnotation(RequestMapping.class);
    //     // 	if (rm != null) {
    //     // 		for (String val : rm.value()) {
    //     //
    //     // 			while (pathVariables != null && val.indexOf("{") < val.indexOf("}")) {
    //     // 				String temp = val.substring(val.indexOf("{") + 1, val.indexOf("}"));
    //     // 				val = val.replace("{" + temp + "}", pathVariables.getString(temp));
    //     // 			}
    //     // 			if ("".equals(val) || methodName.equals(val)) {
    //     // 				return method;
    //     // 			}
    //     // 		}
    //     // 	}
    //     // }
    //     // return null;
    // }

}
