package com.kdgc.hand.controller;

import com.kdgc.hand.service.RedisService;
import com.kdgc.hand.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/redis")
public class RedisTemplateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisTemplateController.class);

    @Resource
    private UserService userService;

    @Resource
    private RedisService redisService;

    //存取redis值
    @RequestMapping("/testRedisHash")
    @ResponseBody
    public Object testCache(Integer id) {
        return userService.testRedisTemplate(id);
    }

    //String
    @RequestMapping("/testRedisString")
    @ResponseBody
    public Object testRedisString() {
        return redisService.testRedisTemplateString();
    }

    //List(可以存列表数据 1，2 ，3，4)
    @RequestMapping("/testRedisList")
    @ResponseBody
    public Object testRedisList() {
       return redisService.testRedisTemplateList();
    }


}
