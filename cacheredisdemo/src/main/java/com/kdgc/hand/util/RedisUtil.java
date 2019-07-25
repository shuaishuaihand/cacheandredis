package com.kdgc.hand.util;

import com.alibaba.fastjson.JSONObject;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis对于String（字符串）、 * List（列表）、Set（集合）、Hash（散列）和 Zset（有序集合）进行操作工具类
 * @author jczhou
 *
 */
@SuppressWarnings("unchecked")
@Component
public class RedisUtil {
    @SuppressWarnings("rawtypes")
    @Autowired
    private RedisTemplate redisTemplate;
    public String type(String key){
    	return redisTemplate.type(key).toString();
    }


    //解决乱码问题
    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
    }



    /**
     * 批量删除对应的value
     * 
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     * 
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }

    /**
     * 删除对应的value
     * 
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     * 
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }


    /**
     * 操作字符串
     * 
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public String get(final String key) {
        Object result = null;
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        if (result == null) {
            return null;
        }
        return result.toString();
    }

    /**
     * 操作hash
     * 
     */
    public boolean setHash(String key, Map<String, Object> value) {
        boolean result = false;
        try {
            redisTemplate.opsForHash().putAll(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean setHash(final String key, Map<String, Object> value, Long expireTime) {
    	boolean result = false;
        try {
            redisTemplate.opsForHash().putAll(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Map<String, Object> getHash(String key) {
        Map<String, Object> result = null;
        try {
            result = redisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
    /**
     * 操作list
     * 
     */
    public boolean setList(String key, List<?> value) {
        boolean result = false;
        try {
        	while (redisTemplate.opsForList().size(key) > 0){
                redisTemplate.opsForList().leftPop(key);
            }
        	ListOperations listOperation = redisTemplate.opsForList();
            if (null != value) {
                int size = value.size();
                for (int i = 0; i < size; i++) {
                    listOperation.rightPush(key, value.get(i));
                }
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean setList(final String key, List<?> value, Long expireTime) {
        boolean result = false;
        try {
        	while (redisTemplate.opsForList().size(key) > 0){
                redisTemplate.opsForList().leftPop(key);
            }
        	ListOperations listOperation = redisTemplate.opsForList();
            if (null != value) {
                int size = value.size();
                for (int i = 0; i < size; i++) {
                    listOperation.rightPush(key, value.get(i));
                }
            }
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Object getList(String key) {
    	List<Object> result = null;
        try {
            result = redisTemplate.opsForList().range(key, 0, -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 操作set
     * 
     */
    public boolean setSet(String key, Set<?> value) {
        boolean result = false;
        try {
            redisTemplate.opsForSet().add(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean setSet(final String key, Set<?> value, Long expireTime) {
        boolean result = false;
        try {
        	redisTemplate.opsForSet().add(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Object getSet(String key) {
    	Set<Object> result = null;
        try {
            result = redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
    public List<Map<String, Object>> getRedis(){
    	List<Map<String, Object>> list = new ArrayList<>();
    	Set<String> set = redisTemplate.keys("*");
    	String thisType = "";
    	for (String key : set) {
    		Map<String, Object> map = new HashMap<String, Object>();
    		thisType = type(key);
    		if("LIST".equals(thisType)){
    			map.put(key, JSONObject.toJSONString(getList(key)));
    		}else if("STRING".equals(thisType)){
    			map.put(key, JSONObject.toJSONString(get(key)));
    		}else if("HASH".equals(thisType)){
    			map.put(key, JSONObject.toJSONString(getHash(key)));
    		}else if("SET".equals(thisType)){
    			map.put(key, JSONObject.toJSONString(getSet(key)));
    		}else if("ZSET".equals(thisType)){
    			
    		}
    		list.add(map);
		}
    	return list;
    }


}

