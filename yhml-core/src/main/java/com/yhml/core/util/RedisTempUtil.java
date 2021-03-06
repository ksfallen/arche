package com.yhml.core.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.connection.RedisConnectionCommands;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Redis 工具类
 */
@Slf4j
public class RedisTempUtil {

    @Setter
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 写入缓存-无超时
     *
     * @param key
     * @param value
     */
    public boolean set(final String key, Object value) {
        if (key == null || value == null) {
            log.info("redis set, key or value is null");
            return false;
        }

        try {
            redisTemplate.opsForValue().set(key, JsonUtil.toJson(value));
            return true;
        } catch (Exception e) {
            log.error("", e);
        }

        return false;
    }

    /**
     * 写入缓存-有超时
     *
     * @param key
     * @param value
     * @param expire 过时时间，以秒为单位
     */
    public boolean set(final String key, Object value, long expire) {
        if (key == null || value == null) {
            log.info("redis set, key or value is null");
            return false;
        }

        try {
            redisTemplate.opsForValue().set(key, JsonUtil.toJson(value), expire, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            log.error("", e);
        }

        return false;
    }


    /**
     * 读取缓存
     *
     * @param key
     * @param clazz
     * @return
     */
    public <T> T get(String key, Class<T> clazz) throws Exception {
        try {
            log.debug("redis get, key:" + key + ",clazz:" + clazz.getName());
            Object val = redisTemplate.boundValueOps(key).get();
            return val == null ? null : JsonUtil.parse(val.toString(), clazz);
        } catch (Exception e) {
            log.error(e.toString());
            throw new Exception(e);
        }
    }

    /**
     * 删除，根据key精确匹配
     *
     * @param key
     */
    public void del(String... key) throws Exception {
        try {
            log.debug("redis del, key:" + Arrays.toString(key));
            redisTemplate.delete(Arrays.asList(key));
        } catch (Exception e) {
            log.error(e.toString());
            throw new Exception(e);
        }
    }

    /**
     * 删除，根据key精确匹配
     *
     * @param key
     */
    public void del(List<String> key) throws Exception {
        try {
            log.debug("redis del, key:" + key);
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.error(e.toString());
            throw new Exception(e);
        }
    }

    /**
     * 批量删除，根据key模糊匹配
     *
     * @param pattern
     */
    public void delWithPattern(String... pattern) throws Exception {
        try {
            log.debug("redis del, pattern:" + Arrays.toString(pattern));
            for (String kp : pattern) {
                redisTemplate.delete(redisTemplate.keys(kp));
            }
        } catch (Exception e) {
            log.error(e.toString());
            throw new Exception(e);
        }
    }

    /**
     * key是否存在
     *
     * @param key
     */
    public boolean exists(String key) throws Exception {
        try {
            log.debug("redis isExistsOrNot, key:" + key);
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error(e.toString());
            throw new Exception(e);
        }
    }

    /**
     * 模糊查询键
     *
     * @param pattern
     * @return
     * @throws Exception
     */
    public Set keysWithPattern(String... pattern) throws Exception {
        Set<String> keys = new HashSet<>();
        try {
            log.debug("redis get keys with pattern, pattern:" + Arrays.toString(pattern));
            for (String kp : pattern) {
                keys.addAll(redisTemplate.keys(kp));
            }
        } catch (Exception e) {
            log.error(e.toString());
            throw new Exception(e);
        }
        return keys;
    }

    /**
     * 清空，慎用
     *
     * @return
     */
    // public boolean flushDB() {
    //     try {
    //         return redisTemplate.execute(new RedisCallback() {
    //             @Override
    //             public Object doInRedis(org.springframework.data.redis.connection.RedisConnection connection) throws
    //             DataAccessException {
    //                 connection.flushDb();
    //                 return "OK";
    //             }
    //         });
    //     } catch (Exception e) {
    //         log.error("", e);
    //     }
    // }

    /**
     * @return
     */
    public long dbSize() throws Exception {
        try {
            return (long) redisTemplate.execute((RedisCallback) RedisServerCommands::dbSize);
        } catch (Exception e) {
            log.error(e.toString());
            throw new Exception(e);
        }
    }

    /**
     * @return
     */
    public String ping() throws Exception {
        try {
            return (String) redisTemplate.execute((RedisCallback) RedisConnectionCommands::ping);
        } catch (Exception e) {
            log.error(e.toString());
            throw new Exception(e);
        }
    }


    /**
     * 获取唯一Id
     *
     * @param key
     * @param delta 增加量（不传采用1）
     * @return
     */
    public Long incrementHash(String key, Long delta) {
        if (null == delta) {
            delta = 1L;
        }
        return redisTemplate.opsForHash().increment("acct_id_" + key, key, delta);
    }

}
