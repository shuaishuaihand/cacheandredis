package com.kdgc.hand.service.Impl;
import com.kdgc.hand.domain.User;
import com.kdgc.hand.mapper.UserMapper;
import com.kdgc.hand.mapper.UserTypeCountMapper;
import com.kdgc.hand.service.RedisService;
import com.kdgc.hand.service.UserService;
import com.kdgc.hand.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserTypeCountMapper userTypeCountMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    //存取redis值（hash类型）
    public  Map<String, Object> testRedisTemplateHash(Integer id){
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



    //String类型
    public Object testRedisTemplateString(){
        String  result= redisUtil.get("USER_TYPE_NUM");
        if(StringUtils.isEmpty(result)){
           List<Map> list= userTypeCountMapper.findUserTypeCountList();
           result=list.toString();
           redisUtil.set("USER_TYPE_NUM",result,300L);
        }
      return result;
    }


    //List类型
    public Object testRedisTemplateList(){
        //List<Map> list= (List<Map>) redisUtil.getList("USER_TYPE_NUM_LIST");
        List<User> list= (List<User>) redisUtil.getList("USER_TYPE_NUM_LIST");
        if (list==null||list.size()==0){
            //list= userTypeCountMapper.findUserTypeCountList();
            list= userMapper.findUserList();
           redisUtil.setList("USER_TYPE_NUM_LIST",list,300L);
        }
        return list;
    }

}
