package com.yhml.core.config.endpoint;// package com.simple.config.mvc.endpoint;
//
// import java.util.LinkedHashMap;
// import java.util.Map;
//
// import org.springframework.beans.BeansException;
// import org.springframework.boot.context.properties.ConfigurationProperties;
// import org.springframework.context.ApplicationContext;
// import org.springframework.context.ApplicationContextAware;
// import org.springframework.stereotype.Component;
// import org.springframework.web.bind.annotation.ResponseBody;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.method.HandlerMethod;
// import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
// import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
// import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
//
// /**
//  * @author: Jfeng
//  * @date: 2018/4/18
//  */
// @Component
// @ConfigurationProperties(prefix = "endpoints.apis")
// public class ApiEndpoint extends AbstractEndpoint<Map<String, Object>> implements ApplicationContextAware {
//
//     private ApplicationContext applicationContext;
//
//     public ApiEndpoint() {
//         super("apis");
//     }
//
//     @Override
//     public Map<String, Object> invoke() {
//         Map<String, Object> result = new LinkedHashMap();
//         this.extractMethodMappings(result);
//         return result;
//     }
//
//     @Override
//     public void setApplicationContext(ApplicationContext context) throws BeansException {
//         this.applicationContext = context;
//     }
//
//     /**
//      * 获取当前环境中,所有RestController提供的接口
//      *
//      * @param applicationContext
//      * @param result
//      */
//     private void extractMethodMappings(Map<String, Object> result) {
//         if (applicationContext == null) {
//             return;
//         }
//
//         for (Object o : applicationContext.getBeansOfType(AbstractHandlerMethodMapping.class).entrySet()) {
//             Map.Entry<String, AbstractHandlerMethodMapping> bean = (Map.Entry) o;
//
//             // 只处理 RequestMappingHandlerMapping
//             if (!bean.getValue().getClass().equals(RequestMappingHandlerMapping.class)) {
//                 continue;
//             }
//
//             Map<?, HandlerMethod> methods = ((AbstractHandlerMethodMapping) bean.getValue()).getHandlerMethods();
//
//             for (Map.Entry<?, HandlerMethod> methodEntry : methods.entrySet()) {
//
//                 HandlerMethod method = methodEntry.getValue();
//
//                 // 只处理 restController 注解的接口
//                 RestController restController = method.getBeanType().getAnnotation(RestController.class);
//                 ResponseBody responseBody = method.getMethod().getAnnotation(ResponseBody.class);
//                 if (restController == null && responseBody == null) {
//                     continue;
//                 }
//
//                 RequestMappingInfo requestMappingInfo = (RequestMappingInfo) methodEntry.getKey();
//                 String url = requestMappingInfo.getPatternsCondition().toString();
//                 Map<String, String> map = new LinkedHashMap();
//                 map.put("beanType", method.getBeanType().getName());
//                 map.put("method", method.getMethod().getName());
//                 map.put("urls", url);
//                 map.put("produces", requestMappingInfo.getProducesCondition().toString());
//                 result.put(url, map);
//             }
//         }
//     }
// }
