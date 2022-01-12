package com.example.app.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService{

    private static final Logger log = LoggerFactory.getLogger(RedisService.class);

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 设置key-value
     *
     * @param key
     * @param value
     */

    public void setKey(Object key, Object value) {
        ValueOperations<Object,Object> ops = redisTemplate.opsForValue();
        ops.set(key, value, 300, TimeUnit.SECONDS);
    }

    /**
     * 获取key
     *
     * @param key
     * @return
     */

    public Object getValue(Object key) {
        ValueOperations<Object, Object> ops = redisTemplate.opsForValue();
        return ops.get(key);
    }

    /**
     * 删除key
     *
     * @param key
     */

    public void delete(Object key) {
        redisTemplate.delete(key);
    }
}
