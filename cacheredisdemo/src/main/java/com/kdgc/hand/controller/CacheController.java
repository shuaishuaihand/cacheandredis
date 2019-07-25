package com.kdgc.hand.controller;

import com.kdgc.hand.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/cache")
public class CacheController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheController.class);

    @Resource
    private UserService userService;

    //添加缓存
    @RequestMapping("/testCache")
    @ResponseBody
    public Object testCache(Integer id) {
        return userService.testSetCache(id);
    }

    //清除缓存
    @RequestMapping("/testEvictCache")
    @ResponseBody
    public Boolean testEvictCache() {
        return userService.testEvictCache();
    }

}
