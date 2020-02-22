package com.bitsun.consumer.common.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-07-17 21:12
 */
@Component
@ConditionalOnClass(RedisTemplate.class)
public class RedisUtil {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisTemplate<String, String> redisStringTemplate;

    @Autowired
    private ValueOperations<String, String> valueOperations;
    @Autowired
    private HashOperations<String, String, Object> hashOperations;
    @Autowired
    private ListOperations<String, Object> listOperations;
    @Autowired
    private SetOperations<String, Object> setOperations;
    @Autowired
    private ZSetOperations<String, Object> zSetOperations;
    /**
     * 默认过期时长，单位：秒
     */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;
    /**
     * 不设置过期时长
     */
    public final static long NOT_EXPIRE = -1;

    public void set(String key, Object value, long expire) {
        valueOperations.set(key, toJson(value));
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public List<String> getList(String key) {
        return redisStringTemplate.opsForList().range(key, 0, -1);
    }

    //批量获取值
    public List<Object> mgetList(List<String> keys){
        return redisTemplate.opsForValue().multiGet(keys);

    }

    public <T> List<T> getObjectList(String key, Class<T> clazz) {
        return (List<T>)this.fromJson(key, clazz);
    }

    public void setStringList(String key, List<String> list) {
        redisStringTemplate.opsForList().rightPushAll(key, list);
    }

    public void set(String key, Object value) {
      //  set(key, value, DEFAULT_EXPIRE);
        redisTemplate.opsForValue().set(key,value);
    }

    public <T> T get(String key, Class<T> clazz, long expire) {
        String value = valueOperations.get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : fromJson(value, clazz);
    }

    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }

    public String get(String key, long expire) {
        String value = valueOperations.get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    public String get(String key) {
        return get(key, NOT_EXPIRE);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object) {
        if (object instanceof Integer || object instanceof Long || object instanceof Float || object instanceof Double
            || object instanceof Boolean || object instanceof String) {
            return String.valueOf(object);
        }
        if (object == null) {
            return "";
        }
        return JSON.toJSONString(object);
    }

    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        return JSON.parseObject(json,clazz);
    }

    /**
     * 模糊查询key
     */
    public Set<String> keys(String key) {
        return redisTemplate.keys(key);
    }

    /**
     * 根据key批量删除
     */
    public void deletes(Set<String> keys) {
        redisTemplate.delete(keys);
    }

    public void setList(String key, Object toJSON) {
        valueOperations.set(key, toJSON.toString());
    }
}
