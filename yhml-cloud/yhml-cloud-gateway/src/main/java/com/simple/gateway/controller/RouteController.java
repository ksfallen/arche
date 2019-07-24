package com.simple.gateway.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Jfeng
 * @date: 2018/4/18
 */
@RestController
public class RouteController {

    @Autowired
    private RouteLocator routeLocator;

    @Autowired
    private DiscoveryClient discovery;

    @Autowired
    private LoadBalancerClient loadBalancer;

    @RequestMapping("/")
    public String index(){
        return "hello gateway...";
    }

    @GetMapping("/refreshRoute")
    public String refresh() {
        return "refresh success";
    }

    @GetMapping("/discovery/{appName}")
    public Object discovery(@PathVariable("appName") String appName) {
        List<ServiceInstance> instances = discovery.getInstances(appName);

        // List<DefaultServiceInstance> list = instances.stream().map(
        //         ins -> new DefaultServiceInstance(
        //                 ins.getServiceId(), ins.getHost(), ins.getPort(), ins.isSecure(), ins.getMetadata()))
        //         .collect(Collectors.toList());

        return instances;
    }

    @GetMapping("/discovery/all")
    public Object all() {
        return discovery.getServices();
    }

    @GetMapping("/choose/{serviceId}")
    public Object choose(@PathVariable("serviceId") String serviceId) {
        return loadBalancer.choose(serviceId);
    }


    @GetMapping("/watchRoute")
    public Object watchNowRoute() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("getRoutes", routeLocator.getRoutes());
        map.put("getIgnoredPaths", routeLocator.getIgnoredPaths());
        return map;
    }
}
