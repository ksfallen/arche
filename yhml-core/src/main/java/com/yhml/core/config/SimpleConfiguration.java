package com.yhml.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author: Jfeng
 * @date: 2018/4/18
 */
// @Configuration
// @PropertySource(value = {"classpath:common.properties"}, ignoreResourceNotFound = true)
public class SimpleConfiguration {

    @Bean
    public RestTemplate customRestTemplate(){
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(3000);
        httpRequestFactory.setConnectTimeout(3000);
        httpRequestFactory.setReadTimeout(3000);

        return new RestTemplate(httpRequestFactory);
    }

    // @Bean
    // public RedisConnectionFactory getRedisConnectionFactory() {
    //     JedisConnectionFactory factory = new JedisConnectionFactory();
    //     factory.setHostName(config.getRedisHost());
    //     factory.setPort(config.getRedisPort());
    //     factory.setPassword(config.getRedisPassword());
    //     factory.setDatabase(config.getRedisDbIndex());
    //     factory.setUsePool(true);
    //     factory.afterPropertiesSet();
    //     return factory;
    // }
    //
    // @Bean
    // @Primary
    // public RedisTemplate<String, Object> getRedisTemplate() {
    //     RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    //     redisTemplate.setConnectionFactory(getRedisConnectionFactory());
    //     redisTemplate.setKeySerializer(new StringRedisSerializer());
    //     redisTemplate.setValueSerializer(jackson2JsonRedisSerializer());
    //     redisTemplate.afterPropertiesSet();
    //     return redisTemplate;
    // }
    //
    // @Bean
    // public CacheManager cacheManager() {
    //     RedisCacheManager rcm = new RedisCacheManager(getRedisTemplate());
    //     ArrayList<String> cacheNames = new ArrayList<>();
    //     cacheNames.add(RedisCache.CACHE_NAME);
    //     rcm.setCacheNames(cacheNames);
    //     rcm.setDefaultExpiration(config.getRedisExpiration());
    //     return rcm;
    // }


}
