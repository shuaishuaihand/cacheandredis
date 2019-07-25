package com.kdgc.hand.service.Impl;
import com.alibaba.fastjson.JSONObject;
import com.kdgc.hand.mapper.UserMapper;
import com.kdgc.hand.service.UserService;
import com.kdgc.hand.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

     //添加缓存
    @Cacheable(cacheNames = "TestCACHE",key = "#root.methodName +'_'+ #id")
    public  Map<String, Object> testSetCache(Integer id){
        Map<String, Object>  user = userMapper.findUserById(id);
        return user;
    }

    //清除缓存
    @CacheEvict(cacheNames = "TestCACHE", allEntries = true)
    public Boolean testEvictCache(){
        return true;
    }

    //存取redis值（hash类型）
    public  Map<String, Object> testRedisTemplate(Integer id){
        Map<String, Object> user=new HashMap<>();
        Map<String, Object> userResult=redisUtil.getHash("USER_BY_ID"+id);
        if(userResult.size()==0){
             user = userMapper.findUserById(id);
             redisUtil.setHash("USER_BY_ID"+id, user,120L);//有效时间120秒，2分钟
        }else{
            user=userResult;
        }

        return user;
    }


}
