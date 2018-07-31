package com.consul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by zhankun on 2018/7/26.
 */
@RestController
@RequestMapping(value = "test")
//自动刷新获取的配置
@RefreshScope
public class HelloController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${test.name}")
    private String name;

    @Value("${test.desc}")
    private String desc;

    @RequestMapping(value = "allServices")
    public List getAllServices(){
        List<ServiceInstance> list = discoveryClient.getInstances("service-producer");
        return list;
    }

    @RequestMapping(value = "findAny")
    public String getAnyServices(){
        String service = loadBalancerClient.choose("service-producer").getUri().toString();
        System.out.println(String.format("-------本次调用到的服务是:%s",service));
        return service;
    }

    @RequestMapping(value = "consumer")
    public String testConsumer(){
        String uri = loadBalancerClient.choose("service-producer").getUri().toString();
        String result = restTemplate.getForObject(uri + "/consul/hello", String.class);
        return result;
    }

    @RequestMapping(value = "testConfig")
    public String testConfig(){
        return String.format("从配置中心获取到的name是:%s;获取到的desc是:%s",name,desc);
    }
}
