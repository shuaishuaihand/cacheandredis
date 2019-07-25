package com.kdgc.hand.service;
import java.util.Map;

public interface UserService {

    Map<String, Object> testSetCache(Integer id);

    Boolean testEvictCache();

    Map<String, Object> testRedisTemplate(Integer id);

}
