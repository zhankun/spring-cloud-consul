package com.consul.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhankun on 2018/7/26.
 */
@RestController
@RequestMapping(value = "consul")
public class HelloController {

    @RequestMapping(value = "hello")
    public String hello(){
        return "hello, i'm consul provider one";
    }
}
