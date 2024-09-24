package com.example.demo.config;

import com.example.demo.LatestSync;
import com.example.demo.bilibili.DetailDtoList;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanSerializer;
import com.fasterxml.jackson.databind.ser.BeanSerializerBuilder;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Configuration
public class CacheConfig {

//
//    @Bean
//    public RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties) {
//
//        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
//
//        // 先载入配置文件中的配置信息
//        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
//
//        // 根据配置文件中的定义，初始化 Redis Cache 配
//        if (redisProperties.getTimeToLive() != null) {
//            redisCacheConfiguration = redisCacheConfiguration.entryTtl(redisProperties.getTimeToLive());
//        }
//        if (StringUtils.hasText(redisProperties.getKeyPrefix())) {
//            redisCacheConfiguration = redisCacheConfiguration.prefixCacheNameWith(redisProperties.getKeyPrefix());
//        }
//        if (!redisProperties.isCacheNullValues()) {
//            redisCacheConfiguration = redisCacheConfiguration.disableCachingNullValues();
//        }
//        if (!redisProperties.isUseKeyPrefix()) {
//            redisCacheConfiguration = redisCacheConfiguration.disableKeyPrefix();
//        }
//
//        // 缓存对象中可能会有 LocalTime/LocalDate/LocalDateTime 等 java.time 段，所以需要通过 JavaTimeModule 定义其序列化、反序列化格式
//        JavaTimeModule javaTimeModule = new JavaTimeModule();
//
//        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSSSSS")));
//        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSSSS")));
//
//        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSSSSS")));
//        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSSSS")));
//
//        // 基于 Jackson 的 RedisSerializer 实现：GenericJackson2JsonRedisSerializer
//        ObjectMapper mapper = new ObjectMapper();
////        mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
////        mapper.enable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
////
////        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//
//        mapper.registerModule(javaTimeModule);
//
//         // Registering Hibernate5Module to support lazy objects for hibernate 5
//        // Use Hibernate4Module if using hibernate 4
//
//        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(mapper);
//
//
//        // 设置 Value 的序列化方式
//        return redisCacheConfiguration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));
//    }

//    @Bean
//    public RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties) {
//
//        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
//
//        // 先载入配置文件中的配置信息
//        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
//
//        // 根据配置文件中的定义，初始化 Redis Cache 配
//        if (redisProperties.getTimeToLive() != null) {
//            redisCacheConfiguration = redisCacheConfiguration.entryTtl(redisProperties.getTimeToLive());
//        }
//        if (StringUtils.hasText(redisProperties.getKeyPrefix())) {
//            redisCacheConfiguration = redisCacheConfiguration.prefixCacheNameWith(redisProperties.getKeyPrefix());
//        }
//        if (!redisProperties.isCacheNullValues()) {
//            redisCacheConfiguration = redisCacheConfiguration.disableCachingNullValues();
//        }
//        if (!redisProperties.isUseKeyPrefix()) {
//            redisCacheConfiguration = redisCacheConfiguration.disableKeyPrefix();
//        }
//
//        // Fastjson 的通用 RedisSerializer 实现
//        GenericFastJsonRedisSerializer serializer  = new GenericFastJsonRedisSerializer();
//
//        // 设置 Value 的序列化方式
//        return redisCacheConfiguration
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer));
//    }
}
