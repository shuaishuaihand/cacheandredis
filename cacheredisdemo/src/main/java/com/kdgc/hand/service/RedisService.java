package com.kdgc.hand.service;
import java.util.Map;

public interface RedisService {

    Map<String, Object> testRedisTemplateHash(Integer id);

    Object testRedisTemplateString();

    Object testRedisTemplateList();


}
