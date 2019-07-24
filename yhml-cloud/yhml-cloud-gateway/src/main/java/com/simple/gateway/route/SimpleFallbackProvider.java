// package com.simple.gateway.route;
//
// import java.io.ByteArrayInputStream;
// import java.io.InputStream;
//
// import javax.servlet.http.HttpServletRequest;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.client.ClientHttpResponse;
// import org.springframework.stereotype.Component;
//
// import com.netflix.loadbalancer.LoadBalancerContext;
// import com.netflix.zuul.context.RequestContext;
// import com.simple.common.base.bean.ResultBean;
// import com.simple.common.base.bean.ResultBeanBuilder;
//
// /**
//  * @author: Jfeng
//  * @date: 2018/4/18
//  */
//
// /**
//  * 降级
//  */
// @Component
// public class SimpleFallbackProvider implements FallbackProvider {
//
//     // @Autowired
//     // private LoadBalancerContext loadBalancerContext;
//
//     @Override
//     public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
//         RequestContext ctx = RequestContext.getCurrentContext();
//         String serviceId = (String) ctx.get("serviceId");
//
//         return new ClientHttpResponse() {
//             @Override
//             public HttpStatus getStatusCode() {
//                 return HttpStatus.OK;
//             }
//
//             @Override
//             public int getRawStatusCode() {
//                 return HttpStatus.OK.value();
//             }
//
//             @Override
//             public String getStatusText() {
//                 return HttpStatus.OK.getReasonPhrase();
//             }
//
//             @Override
//             public void close() {
//
//             }
//
//             @Override
//             public InputStream getBody() {
//                 ResultBean build = ResultBeanBuilder.error().withMessage(serviceId + " 服务不可用").build();
//                 String str = build.toString();
//                 return new ByteArrayInputStream((str.getBytes()));
//             }
//
//             @Override
//             public HttpHeaders getHeaders() {
//                 HttpHeaders headers = new HttpHeaders();
//                 headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//                 return headers;
//             }
//         };
//     }
//
//     @Override
//     public String getRoute() {
//         return null;
//     }
//
// }
