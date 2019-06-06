package com.yhml.core.util;

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RedisClientUtil {

    /**
     * 一天
     */
    private static final long exprie_time = 60 * 60 * 24;

    @Setter
    private static RedissonClient redissonClient;

    /**
     * 写入/覆盖。如果key已经在redis上存在，那么会被覆盖。
     *
     * @param key   键
     * @param value 值。 注意不能超过1G
     * @return
     */
    public static boolean set(String key, String value) {
        return set(key, value, exprie_time);
    }

    public static Long incr(String key) {
        return redissonClient.getAtomicLong(key).incrementAndGet();
    }


    /**
     * 写入/覆盖。如果key已经在redis上存在，那么会被覆盖。
     *
     * @param key
     * @param value
     * @param seconds 存活时间（秒）
     * @return
     */
    public static boolean set(String key, String value, long seconds) {
        try {
            redissonClient.getBucket(key, StringCodec.INSTANCE).set(value, seconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("set error, k={}, v={}", key, value, e);
            return false;
        }
        return true;
    }

    /**
     * 批量set key和vaue 对
     * @param kv
     */
    public static void mset(String... kv) {
        if (kv == null || kv.length % 2 != 0) {
            throw new IllegalArgumentException("key,value... " + Arrays.toString(kv));
        }

        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < kv.length; ) {
            map.put(kv[i++], kv[i++]);
        }

       mset(map);
    }

    public static void mset(Map<String, String> map) {
        try {
            redissonClient.getBuckets(StringCodec.INSTANCE).set(map);
        } catch (Exception e) {
            log.error("mset error, map={}", map, e);
        }
    }


    /**
     * 不存在key 则写入。即：set if Not eXists
     * <p>
     * 如果redis上已经有key，什么都不错; 如果redis上没有key，则写入;
     * </p>
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean setnx(String key, String value) {
        return setnx(key, value, exprie_time);
    }

    /**
     * 不存在key 则写入。即：set if Not eXists
     * <p>
     * 如果redis上已经有key，什么都不错; 如果redis上没有key，则写入;
     * </p>
     *
     * @param key
     * @param value
     * @param seconds 存活时间（秒）
     * @return
     */
    public static boolean setnx(String key, String value, long seconds) {
        try {
            // NX:只在键不存在时，才对键进行设置操作;
            redissonClient.getBucket(key, StringCodec.INSTANCE).trySet(value, seconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("setnx error, k={}, v={}", key, value, e);
            return false;
        }

        return true;
    }

    /**
     * 删除
     *
     * @param key
     * @return
     */
    public static long del(String... keys) {
        try {
            return redissonClient.getKeys().delete(keys);
        } catch (Exception e) {
            log.error("del error, k={}", Arrays.toString(keys), e);
        }

        return 0;
    }

    /**
     * 匹配删除 keys
     * @param pattern
     * @return
     */
    public static long delByPattern(String pattern) {
        try {
            RKeys rKeys = redissonClient.getKeys();
            return rKeys.deleteByPattern(pattern);
        } catch (Exception e) {
            log.error("delByPattern error, k={}",pattern, e);
        }
        return 0;
    }

    /**
     * 读取
     */
    public static String get(String key) {
        try {
            RBucket<String> bucket = redissonClient.getBucket(key, StringCodec.INSTANCE);
            return bucket.get();
        } catch (Exception e) {
            log.error("get error, k={}", key, e);
        }

        return "";
    }

    /**
     * 增加到set集合
     */
    public static void zadd(String key, Double score, String value) {
        try {
            RScoredSortedSet<String> set = redissonClient.getScoredSortedSet(key, StringCodec.INSTANCE);
            set.add(score, value);
        } catch (Exception e) {
            log.error("zadd error, k={}, v={}", key, value, e);
        }

    }

    /**
     * 将一个或多个member元素加入到集合key当中，已经存在于集合的member元素将被忽略。
     *
     * @param key
     * @param value
     * @author HuJianfeng
     */
    public static void sadd(String key, String... members) {

    }

    /**
     * 返回集合key中的所有成员。
     *
     * @param key
     * @param value
     * @author HuJianfeng
     */
    public static Set<String> smembers(String key) {
        return null;
    }

    /**
     * 移除并返回集合中的一个随机元素
     *
     * @param key
     * @return
     * @author HuJianfeng
     */
    public static String spop(String key) {
        return null;
    }

    /**
     * 返回有序集合成员的数量
     *
     * @param key
     * @return
     * @author HuJianfeng
     */
    public static int zcard(String key) {
       return 0;
    }

    /**
     * 返回一个范围的有序集合 <br>
     * 0表示有序集第一个成员，1表示有序集第二个成员 <br>
     * -1表示最后一个成员，-2表示倒数第二个成员
     *
     * @param key
     * @param start
     * @param end
     * @return
     * @author HuJianfeng
     */
    public static Set<String> zrange(String key, long start, long end) {
        return null;
    }

    /**
     * 返回有序集合<br>
     * score值介于min和max之间(包括等于min或max)
     *
     * @param key
     * @param mix
     * @param max
     * @return
     * @author HuJianfeng
     */
    public static Set<String> zrangeByScore(String key, double mix, double max) {
        return null;
    }

    /**
     * 从集合中删除
     */
    public static void zrem(String key, String... value) {

    }

    /**
     * 从set中查询value的score
     *
     * @param key
     * @param value
     * @return
     */
    public static Double zscore(String key, String value) {
        // Double score = null;
        // Pool<Jedis> pool = RedisClientUtil.getJedisPool();
        // if (pool == null) {
        //     log.info("从set中查询score(权重)出错:无法获取redis连接");
        //     return score;
        // }
        // Jedis jedis = pool.getResource();
        // try {
        //     score = jedis.zscore(key, value);
        // } finally {
        //     pool.returnResource(jedis);
        // }
        // return score;

        return null;
    }


    /**
     * 在set集合中根据最小权重和最大权重得出中间的数目
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public static int zcount(String key, double min, double max) {
        // Long count = 0L;
        // Pool<Jedis> pool = RedisClientUtil.getJedisPool();
        // if (pool == null) {
        //     return 0;
        // }
        // Jedis jedis = pool.getResource();
        // try {
        //     count = jedis.zcount(key, min, max);
        // } finally {
        //     pool.returnResource(jedis);
        // }
        // return count.intValue();

        return 0;
    }

    /**
     * 模糊查找key
     *
     * @param key 查询条件[eg: config.core.* ]
     * @return
     */
    public static Set<String> keys(String key) {
        return null;
    }

    public static Long rpush(String key, String... values) {
        // Pool<Jedis> pool = getJedisPool();
        // if (pool == null) {
        //     return null;
        // }
        // Jedis jedis = pool.getResource();
        // try {
        //     return jedis.rpush(key, values);
        // } finally {
        //     pool.returnResource(jedis);
        // }

        return null;
    }

    public static List<String> blpop(String... key) {
        return blpop(0, key);
    }

    /**
     * 当列表为空时，连接将被阻塞，直到等待超时或发现可弹出元素为止
     * 超时参数设为0表示阻塞时间可以无限期延长
     *
     * @param timeout
     * @param key
     * @return
     * @author HuJianfeng
     */
    public static List<String> blpop(int timeout, String... key) {
        return null;
    }

}
