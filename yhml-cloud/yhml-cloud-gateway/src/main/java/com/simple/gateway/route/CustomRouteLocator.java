package com.simple.gateway.route;


import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class CustomRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {

    private ZuulProperties properties;

    public CustomRouteLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
        this.properties = properties;
        log.info("servletPath:{}", servletPath);
    }

    //父类已经提供了这个方法，这里写出来只是为了说明这一个方法很重要！！！  
    @Override
    public void refresh() {
        doRefresh();
    }

    @Override
    protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulProperties.ZuulRoute> routesMap = new LinkedHashMap<>();
        //从application.properties中加载路由信息  
        routesMap.putAll(super.locateRoutes());
        //从db中加载路由信息  
        routesMap.putAll(locateRoutesFromDB());
        //优化一下配置  
        LinkedHashMap<String, ZuulProperties.ZuulRoute> values = new LinkedHashMap<>();
        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : routesMap.entrySet()) {
            String path = entry.getKey();
            // Prepend with slash if not already present.  
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (StringUtils.hasText(this.properties.getPrefix())) {
                path = this.properties.getPrefix() + path;
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
            }
            values.put(path, entry.getValue());
        }
        return values;
    }

    private Map<String, ZuulProperties.ZuulRoute> locateRoutesFromDB() {
        Map<String, ZuulProperties.ZuulRoute> routes = new LinkedHashMap<>();
        //获取路由配置数据
        // List<GatewayApiDefineEntity> list = gatewayApiDefineService.findByEnabled(true);
        // for (GatewayApiDefineEntity entity : list) {
        //     if (StringUtils.isEmpty(entity.getPath())) {
        //         continue;
        //     }
        //     if (StringUtils.isEmpty(entity.getServiceId()) && StringUtils.isEmpty(entity.getUrl())) {
        //         continue;
        //     }
        //     ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
        //     try {
        //         BeanUtils.copyProperties(entity, zuulRoute);
        //         Set<String> headers = new HashSet<>();
        //         if (!StringUtils.isEmpty(entity.getSensitiveHeadersStr())) {
        //             String[] strings = entity.getSensitiveHeadersStr().split(",");
        //             for (int i = 0; i < strings.length; i++) {
        //                 headers.add(strings[i]);
        //             }
        //         }
        //         zuulRoute.setSensitiveHeaders(headers);
        //         zuulRoute.setId(entity.getZuulId());
        //     } catch (Exception e) {
        //         logger.error("=============load zuul route info from db with error==============", e);
        //     }
        //     routes.put(zuulRoute.getPath(), zuulRoute);
        // }
        return routes;
    }
} 
